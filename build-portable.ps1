<#
  Builds a self-contained, portable Windows app image for TimetableXpert.

  Output:  dist\TimetableXpert\TimetableXpert.exe   (double-click to run)
           dist\TimetableXpert-1.0-win64.zip        (zip to hand out)

  The image bundles its own Java runtime and the MariaDB engine, so the
  end user installs nothing and never touches a database. First launch
  creates  %LOCALAPPDATA%\TimetableXpert  and imports the schema.

  Requires: a JDK 17+ (for jlink / jpackage) on PATH or in JAVA_HOME.
            Tested with Temurin JDK 25.
#>

$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
Set-Location $root

# --- locate the JDK tools -----------------------------------------------------
function Find-Tool($name) {
    if ($env:JAVA_HOME -and (Test-Path "$env:JAVA_HOME\bin\$name.exe")) { return "$env:JAVA_HOME\bin\$name.exe" }
    $c = Get-Command "$name.exe" -ErrorAction SilentlyContinue
    if ($c) { return $c.Source }
    throw "$name not found. Set JAVA_HOME to a JDK 17+ or put it on PATH."
}
$jlink    = Find-Tool jlink
$jpackage = Find-Tool jpackage
Write-Host "jlink    : $jlink"
Write-Host "jpackage : $jpackage"

# --- locate Maven (wrapper is incomplete in this repo) -----------------------
$mvn = $null
if ((Test-Path "$root\mvnw.cmd") -and (Test-Path "$root\.mvn\wrapper\maven-wrapper.jar")) { $mvn = "$root\mvnw.cmd" }
if (-not $mvn) {
    $cached = Get-ChildItem "$env:USERPROFILE\.m2\wrapper\dists" -Recurse -Filter mvn.cmd -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($cached) { $mvn = $cached.FullName }
}
if (-not $mvn) {
    $c = Get-Command mvn.cmd -ErrorAction SilentlyContinue
    if ($c) { $mvn = $c.Source }
}
if (-not $mvn) { throw "Maven not found (no working wrapper, no cached dist, not on PATH)." }
Write-Host "maven    : $mvn"

# --- 1. build the fat jar ---------------------------------------------------
Write-Host "`n[1/4] mvn clean package ..." -ForegroundColor Cyan
& $mvn -q -DskipTests clean package
if ($LASTEXITCODE -ne 0) { throw "Maven build failed." }
$jar = "$root\target\TimetableXpert.jar"
if (-not (Test-Path $jar)) { throw "Expected $jar was not produced." }
Write-Host ("      jar: {0:N1} MB" -f ((Get-Item $jar).Length / 1MB))

# --- 2. trimmed Java runtime --------------------------------------------------
Write-Host "`n[2/4] jlink runtime ..." -ForegroundColor Cyan
$rt = "$root\target\runtime"
if (Test-Path $rt) { Remove-Item -Recurse -Force $rt }
# JDK modules used by JavaFX (from classpath), POI, JasperReports, MariaDB4j and
# the MariaDB JDBC driver. Generous on purpose - a missing one is a hard crash.
$modules = @(
  'java.base','java.desktop','java.sql','java.naming','java.management',
  'java.security.jgss','java.security.sasl','java.scripting','java.xml',
  'java.xml.crypto','java.prefs','java.logging','java.transaction.xa',
  'java.datatransfer','java.rmi','java.compiler','java.instrument',
  'jdk.crypto.ec','jdk.crypto.cryptoki','jdk.unsupported','jdk.zipfs',
  'jdk.charsets','jdk.localedata','jdk.accessibility','jdk.xml.dom'
) -join ','
& $jlink --add-modules $modules --output $rt `
    --strip-debug --no-header-files --no-man-pages --compress=2
if ($LASTEXITCODE -ne 0) { throw "jlink failed." }

# --- 3. jpackage app image -------------------------------------------------
Write-Host "`n[3/4] jpackage app-image ..." -ForegroundColor Cyan
$staging = "$root\target\jpackage-input"
if (Test-Path $staging) { Remove-Item -Recurse -Force $staging }
New-Item -ItemType Directory -Path $staging | Out-Null
Copy-Item $jar "$staging\TimetableXpert.jar"

$dist = "$root\dist"
if (Test-Path "$dist\TimetableXpert") { Remove-Item -Recurse -Force "$dist\TimetableXpert" }
New-Item -ItemType Directory -Path $dist -Force | Out-Null

$icon = "$root\src\main\resources\com\timetablexpert\icon.ico"
$iconArg = @()
if (Test-Path $icon) { $iconArg = @('--icon', $icon) }

& $jpackage `
    --type app-image `
    --name TimetableXpert `
    --app-version 1.0 `
    --vendor "TimetableXpert" `
    --description "Academic timetable generator" `
    --input $staging `
    --main-jar TimetableXpert.jar `
    --main-class com.timetablexpert.GUIStarter `
    --runtime-image $rt `
    --dest $dist `
    --java-options "-Xmx768m" `
    --java-options "-Dfile.encoding=UTF-8" `
    @iconArg
if ($LASTEXITCODE -ne 0) { throw "jpackage failed." }

# --- 4. zip ---------------------------------------------------------------
Write-Host "`n[4/4] zipping ..." -ForegroundColor Cyan
$zip = "$dist\TimetableXpert-1.0-win64.zip"
if (Test-Path $zip) { Remove-Item -Force $zip }
Compress-Archive -Path "$dist\TimetableXpert\*" -DestinationPath $zip
Write-Host ("      zip: {0:N1} MB" -f ((Get-Item $zip).Length / 1MB))

Write-Host "`nDone." -ForegroundColor Green
Write-Host "  Run    : $dist\TimetableXpert\TimetableXpert.exe"
Write-Host "  Share  : $zip"
Write-Host "  Login  : admin / admin"

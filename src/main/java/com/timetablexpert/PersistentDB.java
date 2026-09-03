package com.timetablexpert;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfiguration;

/**
 * MariaDB4j's {@link DB#newEmbeddedDB(DBConfiguration)} always runs
 * {@code mysql_install_db} on start-up. That is correct for its default throw-away
 * data directory, but we keep a permanent one under {@code %LOCALAPPDATA%} so the
 * user's timetable data survives restarts - and re-initialising an already
 * initialised data directory makes {@code mysql_install_db.exe} exit with an
 * error on the second launch.
 *
 * <p>This subclass reproduces the {@code newEmbeddedDB} start-up sequence but
 * skips the install step once the system schema is already present.
 */
final class PersistentDB extends DB {

    private final boolean skipInstall;

    private PersistentDB(DBConfiguration config, boolean skipInstall) {
        super(config);
        this.skipInstall = skipInstall;
        // Give the server longer to come up: after an unclean shutdown MariaDB
        // replays the redo log on start, which can exceed the 30 s default.
        this.dbStartMaxWaitInMS = 90_000;
    }

    static PersistentDB create(DBConfiguration config, boolean alreadyInitialised)
            throws ManagedProcessException {
        PersistentDB db = new PersistentDB(config, alreadyInitialised);
        db.prepareDirectories();
        db.unpackEmbeddedDb();
        db.install();
        return db;
    }

    @Override
    protected synchronized void install() throws ManagedProcessException {
        if (skipInstall) {
            return;
        }
        super.install();
    }
}

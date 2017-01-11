package org.iguanatool.search.evolve.migration;

import org.iguanatool.search.evolve.population.CoPopulation;

/**
 * User: phil
 * Date: 18-Feb-2006
 * Time: 13:28:59
 */
public abstract class MigrationManager {

    protected int migrateEvery;
    protected int generationsToMigration = 0;
    protected MigrantSelector migrantSelector;

    public MigrationManager(int migrateEvery,
                            MigrantSelector migrantSelector) {
        this.migrateEvery = migrateEvery;
        this.migrantSelector = migrantSelector;
    }

    public void migration(CoPopulation coPopulation) {
        generationsToMigration++;
        if (generationsToMigration == migrateEvery) {
            doMigration(coPopulation);
            generationsToMigration = 0;
        }
    }

    public void reset() {
        generationsToMigration = 0;
    }

    protected abstract void doMigration(CoPopulation coPopulation);
}

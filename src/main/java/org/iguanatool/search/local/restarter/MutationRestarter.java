package org.iguanatool.search.local.restarter;

import org.iguanatool.search.evolve.mutation.MutationOperator;
import org.iguanatool.search.solution.Solution;

public class MutationRestarter extends Restarter {

    private MutationOperator mutationOperator;

    public MutationRestarter(int restartType, MutationOperator mutationOperator) {
        super(restartType);
        this.mutationOperator = mutationOperator;
    }

    protected Solution generateRestartSolution(Solution currentSolution) {
        Solution newSolution = (Solution) currentSolution.clone();
        mutationOperator.mutate(newSolution);
        return newSolution;
    }
}

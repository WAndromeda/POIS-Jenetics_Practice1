import io.jenetics.*;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

public class Main {
    public static void main(String[] args){

        int populationSize = 400;
        int numberOfGenerations = 15;

        final Engine<EnumGene<Integer>, Double> engine = Engine.builder(TravelingSalesman::dist, Codecs.ofPermutation(TravelingSalesman.STOPS))
                .optimize(Optimize.MINIMUM)
                .maximalPhenotypeAge(11)
                .populationSize(populationSize)
                .alterers(new SwapMutator<>(0.2), new PartiallyMatchedCrossover<>(0.35))
                .build();

        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        final Phenotype<EnumGene<Integer>, Double> best = engine.stream()
                .limit(bySteadyFitness(numberOfGenerations))
                .limit(250)
                .peek(statistics)
                .collect(toBestPhenotype());

        System.out.println(statistics);
        System.out.println(best);
    }
}

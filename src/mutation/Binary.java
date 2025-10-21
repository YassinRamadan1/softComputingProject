package mutation;

import chromosome.Chromosome;

public class Binary implements BinaryMutation {
    private float Pm = 0.01f;

    @Override
    public Chromosome mutateFlip(Chromosome chromosome) {
        for (int i = 0; i < chromosome.getGenes().length; i++) {
            float randomNumber = (float) (Math.random() * 1f);
            if (randomNumber < Pm) {
                chromosome.getGenes()[i] = !(boolean) chromosome.getGenes()[i];
            }
        }
        return chromosome;
    }
}

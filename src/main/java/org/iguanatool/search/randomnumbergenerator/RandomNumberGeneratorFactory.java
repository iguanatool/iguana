package org.iguanatool.search.randomnumbergenerator;

/**
 * Created by phil on 10/01/2017.
 */
public class RandomNumberGeneratorFactory {

    public static RandomNumberGenerator instantiateGenerator(String name) {
        if (!name.endsWith("Generator")) {
            name += "RandomNumberGenerator";
        }

        name = "org.iguanatool.search.randomnumbergenerator."+ name;

        System.out.println(name);

        try {
            return (RandomNumberGenerator) Class.forName(name).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}

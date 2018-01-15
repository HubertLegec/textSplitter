package com.pw.eiti.wedt.network;

import org.encog.engine.network.activation.ActivationElliott;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;

/**
 * This class provides methods that allow to:
 *  - create new network of specified size
 *  - save network to file
 *  - restore network from file
 */
public class NetworkProvider {
    private NetworkProvider() {}

    public static BasicNetwork createNetwork(int inputSize) {
        final BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, inputSize));
        network.addLayer(new BasicLayer(new ActivationElliott(), false, 1));
        network.getStructure().finalizeStructure();
        network.reset();
        return network;
    }

    public static BasicNetwork restoreSavedNetwork(String modelPath) {
        return  (BasicNetwork) EncogDirectoryPersistence.loadObject(new File(modelPath));
    }

    public static void saveToFile(String modelPath, BasicNetwork network) {
        EncogDirectoryPersistence.saveObject(new File(modelPath), network);
    }
}

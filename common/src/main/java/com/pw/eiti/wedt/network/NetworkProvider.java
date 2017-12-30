package com.pw.eiti.wedt.network;

import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;

public class NetworkProvider {
    public static BasicNetwork createNetwork(int inputSize) {
        final BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationLinear(), true, inputSize));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
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

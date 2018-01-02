package com.pw.eiti.wedt;

import javafx.scene.control.TreeItem;

import java.util.Collection;

public class XMLTreeViewGenerator {
    static TreeItem<String> generateTree(Collection<String> sections) {
        TreeItem<String> root = new TreeItem<>("document");
        root.setExpanded(true);
        sections.stream()
                .map(s -> {
                    TreeItem<String> i = new TreeItem<>("p");
                    i.getChildren().add(new TreeItem<>(s.trim()));
                    return i;
                })
                .forEach(n -> root.getChildren().add(n));
        return root;
    }
}

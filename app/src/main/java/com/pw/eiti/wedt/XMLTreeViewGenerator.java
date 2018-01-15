package com.pw.eiti.wedt;

import javafx.scene.control.TreeItem;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Utils class. Generates paragraphs tree to present in application preview
 */
public class XMLTreeViewGenerator {
    /**
     * Generates paragraphs tree from given list of paragraphs
     * @param sections list of paragraphs, where each paragraph is represented by its content
     * @return paragraphs tree
     */
    static TreeItem<String> generateTree(Collection<String> sections) {
        TreeItem<String> root = new TreeItem<>("document");
        root.setExpanded(true);
        List<TreeItem<String>> paras = sections.stream()
                .map(XMLTreeViewGenerator::mapSectionToParagraph)
                .collect(toList());
        root.getChildren().addAll(paras);
        return root;
    }

    private static TreeItem<String> mapSectionToParagraph(String section) {
        TreeItem<String> i = new TreeItem<>("p");
        i.getChildren().add(new TreeItem<>(section.trim()));
        return i;
    }
}

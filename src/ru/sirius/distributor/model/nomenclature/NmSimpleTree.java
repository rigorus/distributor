package ru.sirius.distributor.model.nomenclature;

import java.util.Map;
import ru.sirius.distributor.db.NomenclatureHelper;
import ru.sirius.distributor.model.Group;

//TODO предполагается копировать каждый раз с оригинала
// Реализовать два интерфейса один для изменения другой для работы
public class NmSimpleTree {

    private NmSimpleNode root;
    private Map<Integer, NmSimpleNode> nodes;

    private NmSimpleTree() {

        for (Group group : NomenclatureHelper.getGROUPS().values()) {
            NmSimpleNode node = new NmSimpleNode(NmNode.NodeType.GROUP, group.getId());
            nodes.put(group.getId(), node);
            for (int article : group.getArticles()) {
                node.addChild(new NmSimpleNode(NmNode.NodeType.ARTICLE, article));
            }
        }

        for (NmSimpleNode node : nodes.values()) {
            Group group = NomenclatureHelper.getGROUPS().get(node.getId());
            if (group.getParentId() == 0) {
                root = node;
            } else {
                node.parent = nodes.get(group.getParentId());
                nodes.get(group.getParentId()).addChild(node);
            }
        }

        root = null;
    }

    public static NmSimpleTree getInstance() {
        return NmSimpleTreeHolder.INSTANCE;
    }

    private static class NmSimpleTreeHolder {

        private static final NmSimpleTree INSTANCE = new NmSimpleTree();
    }

    public NmSimpleNode getRoot() {
        return root;
    }

    public TreeBuilder getTreeBuider() {

        return null;
    }
}

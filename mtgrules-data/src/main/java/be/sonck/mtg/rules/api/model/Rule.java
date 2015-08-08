package be.sonck.mtg.rules.api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.copyOf;

/**
 * Created by johansonck on 14/07/15.
 */
public class Rule {

    private final String id;
    private final String text;

    private Rule parent;
    private List<Rule> children = new ArrayList<>();


    public Rule(String id, String text) {
        checkArgument(id != null, "id cannot be null");
        checkArgument(text != null, "text cannot be null");

        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Rule getParent() {
        return parent;
    }

    public void setParent(Rule parent) {
        checkParentState(parent);

        if (this.parent == parent) return;

        this.parent = parent;
        parent.addChild(this);
    }

    private void checkParentState(Rule parent) {
        if (this.parent != null && this.parent != parent) {
            throw new IllegalStateException("rule " + this.getId() + " is already a child of rule " + this.parent.getId());
        }
    }

    public Collection<Rule> getChildren() {
        return copyOf(children);
    }

    void addChild(Rule child) {
        children.add(child);
    }
}

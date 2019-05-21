package ru.job4j.additionaltasks.dbscripts;

import java.util.Stack;

public class Grabber {

    private Stack<Integer> stack = new Stack<>();

    public void getDependenciesFromObj(VulnerabilityScriptWithoutDb in) {
        this.stack.push(in.getScriptId());
        for (VulnerabilityScriptWithoutDb v : in.getDependencies()) {
            this.getDependenciesFromObj(v);
        }
    }

    public int getDependencyID() {
        return this.stack.pop();
    }
}
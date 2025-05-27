package app.model.simulation;

import app.model.structure.ShireMap;

import java.util.List;

public class AlgorithmRunner implements Runnable {
    private String output;
    private List<String> outputList;
    private ShireMap map;
    private String algorithmType;

    public AlgorithmRunner(ShireMap map, String algorithmType) {
        this.map = map;
        this.algorithmType = algorithmType;
        this.output = "";
    }

    public String getOutput() {
        return output;
    }

    public List<String> getOutputList() {return outputList;}

    @Override
    public void run() {
        // N - simulateWholeProcess, A - simulateWholeProcessWithActivation, Q - simulateWholeProcessWithQuarters
        if(this.algorithmType.equals("N")){
            this.output = this.map.simulateWholeProcess();
        }
        else if(this.algorithmType.equals("A")){
            this.output = this.map.simulateWholeProcessWithActivation();
        }
        else if(this.algorithmType.equals("Q")){
            this.outputList = this.map.simulateWholeProcessWithQuarters();
        }
    }
}

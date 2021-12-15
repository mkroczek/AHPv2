package miapd.ahp.objects;

import Jama.Matrix;
import Jama.EigenvalueDecomposition;
import java.lang.*;

import java.util.Arrays;

public class ComparisonMatrix {
    private final double[][] comparisonMatrix;
    private final int size;
    private String name = null;

    public ComparisonMatrix(int size){
        this.comparisonMatrix = new double[size][size];
        this.size = size;

        this.resetMatrix();
    }

    public ComparisonMatrix(int size, String name){
        this.comparisonMatrix = new double[size][size];
        this.size = size;
        this.name = name;

        this.resetMatrix();
    }

    private void resetMatrix(){
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                if (i == j) {
                    this.comparisonMatrix[i][j] = 1;
                }
                else {
                    this.comparisonMatrix[i][j] = 0;
                }
            }
        }
    }

    public void printMatrix(){
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                System.out.print(comparisonMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public double[][] getComparisonMatrix(){
        return this.comparisonMatrix;
    }

    public int getSize(){
        return this.size;
    }

    public double getValueFromIndex(int i, int j){
        return this.comparisonMatrix[i][j];
    }

    public double getRating(int x, int y) {
        return this.comparisonMatrix[x][y];
    }

    public void setRating(int x, int y, double rating) {
        this.comparisonMatrix[x][y] = rating;
    }

    private boolean checkIfComplete(){
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                if(comparisonMatrix[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    private double[][] getAuxiliaryMatrixEV(){
        double[][] auxiliaryMatrix = new double[this.size][this.size];
        for(int i=0; i<this.size; i++){
            int zerosCounter = 0;
            for(int j=0; j<this.size; j++){
                if (i != j){
                    if(comparisonMatrix[i][j] == 0){
                        zerosCounter = zerosCounter + 1;
                    }
                    else{
                        auxiliaryMatrix[i][j] = comparisonMatrix[i][j];
                    }
                }
            }
            auxiliaryMatrix[i][i] = zerosCounter + 1;
        }

        return auxiliaryMatrix;
    }

    public double[] calculateEV(){
        Matrix matrix;
        if (checkIfComplete()){
            matrix = new Matrix(this.comparisonMatrix);
        }
        else {
            matrix = new Matrix(this.getAuxiliaryMatrixEV());
        }
        EigenvalueDecomposition EVD = new EigenvalueDecomposition(matrix);

        double[] eigenvaluesR = EVD.getRealEigenvalues();
        double[] eigenvaluesI = EVD.getImagEigenvalues();

        double max = -1.0;
        int maxIndex = 0;
        for(int i=0; i<eigenvaluesR.length; i++){
            if (eigenvaluesR[i] > max && eigenvaluesI[i] == 0.0){
                max = eigenvaluesR[i];
                maxIndex = i;
            }
        }

        double[][] eigenvectors = EVD.getV().getArrayCopy();
        double[] eigenvector = new double[this.size];

        double sum = 0.0;
        for (int i=0; i<this.size; i++){
            eigenvector[i] = eigenvectors[i][maxIndex];
            sum = sum + eigenvector[i];
        }

        for (int i=0; i<this.size; i++){
            eigenvector[i] = eigenvector[i] / sum;
        }

        System.out.println(Arrays.toString(eigenvector));

        return eigenvector;
    }

    private double[][] getAuxiliaryMatrixGM(){
        double[][] auxiliaryMatrix = new double[this.size][this.size];

        for (int i=0; i<this.size; i++){
            int constantsCounter = 0;
            for (int j=0; j<this.size; j++){
                if (i != j){
                    if(comparisonMatrix[i][j] != 0){
                        constantsCounter = constantsCounter + 1;
                        auxiliaryMatrix[i][j] = 0;
                    }
                    else{
                        auxiliaryMatrix[i][j] = 1;
                    }
                }
            }
            auxiliaryMatrix[i][i] = constantsCounter + 1;
        }

        return auxiliaryMatrix;
    }

    private double[][] getConstantsMatrixGM(){
        double[][] constantsVector = new double[this.size][1];

        for (int i=0; i<this.size; i++){
            constantsVector[i][0] = 0;
            for(int j=0; j<this.size; j++){
                if(i != j){
                    if(this.comparisonMatrix[i][j] != 0){
                        constantsVector[i][0] = constantsVector[i][0] + Math.log(comparisonMatrix[i][j]);
                    }
                }
            }
        }
        System.out.println("r = "+Arrays.deepToString(constantsVector));
        return constantsVector;
    }

    public double[] calculateGM(){
        Matrix matrixB;
        if (checkIfComplete()){
            matrixB = new Matrix(this.comparisonMatrix);
        }
        else {
            matrixB = new Matrix(this.getAuxiliaryMatrixGM());
        }
//        Matrix matrixB = new Matrix(this.getAuxiliaryMatrixGM());
        Matrix matrixLogC = new Matrix(this.getConstantsMatrixGM());
        Matrix matrixLogW = matrixB.solve(matrixLogC);

        double[][] tmpLogW = matrixLogW.getArrayCopy();
        double[] vectorW = new double[this.size];

        double sum = 0.0;
        for (int i=0; i<this.size; i++){
            double value = Math.exp(tmpLogW[i][0]);
            vectorW[i] = value;
            sum = sum + value;
        }

        for (int i=0; i<this.size; i++){
            vectorW[i] = vectorW[i]/sum;
        }

        return vectorW;
    }

    public double calculateSaatysIC(){
        Matrix matrix = new Matrix(this.getAuxiliaryMatrixEV());
        EigenvalueDecomposition EVD = new EigenvalueDecomposition(matrix);

        double[] eigenvaluesR = EVD.getRealEigenvalues();
        double[] eigenvaluesI = EVD.getImagEigenvalues();

        double max = -1.0;
        int maxIndex = 0;
        for(int i=0; i<eigenvaluesR.length; i++){
            if (eigenvaluesR[i] > max && eigenvaluesI[i] == 0.0){
                max = eigenvaluesR[i];
                maxIndex = i;
            }
        }

        return (max - this.size)/(this.size - 1);
    }

    public double calculateGCI(){
        double[] vectorW = this.calculateGM();

        int counter = 0;
        double consistencyIndex = 0.0;

        for(int i = 0; i<this.size; i++){
            for (int j = i+1; j<this.size; j++){
                if(this.comparisonMatrix[i][j] != 0){
                    counter = counter + 1;
                    consistencyIndex = consistencyIndex +
                            Math.pow(Math.log(this.comparisonMatrix[i][j]*vectorW[i]/vectorW[j]), 2);
                }
            }
        }

        //Z ksiązki
        if(checkIfComplete()){
            counter = counter * this.size/(this.size - 2);
        }

        return consistencyIndex/counter;
    }



}

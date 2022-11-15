package junit.practice.arithmetic;

public class CalcMain {
    public static void main(String[] args) {
        
    }

    public int calc(int op, int valL, int valR){
        int result = 0;

        switch(op){
            case 1:
                result = valL + valR;
                break;
            case 2:
                result = valL - valR;
                break;
            case 3:
                result = valL * valR;
                break;
            case 4:
                result = valL / valR;
                break;
            default:
                result = 0;
        }

        return result;
    }
}

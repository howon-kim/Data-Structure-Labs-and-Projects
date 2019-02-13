public class OffByN implements CharacterComparator{
    private int offSet;

    public OffByN(int N){
        offSet = N;
    }

    @Override
    public boolean equalChars(char x, char y){
        if (Math.abs(x - y) == offSet) {
            return true;
        } else {
            return false;
        }
    }
}

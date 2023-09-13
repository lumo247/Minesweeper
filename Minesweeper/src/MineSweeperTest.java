public class MineSweeperTest {
    public static void main(String[] args) {
        Board test = new Board();

        System.out.println(test.bombs());

        System.out.println(test.numSurroundingBombs(0, 4));

        test.destroyTile(0, 0);

        System.out.println(test);
    }
}

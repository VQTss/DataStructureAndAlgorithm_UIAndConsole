package binarysearchtreeconsole;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author Thai Vo Quoc CE160568
 */
public class BinarySearchTreeConsole {

    /**
     * @param args the command line arguments
     */
    private int[] a;
    private int n;
    BSTree tree = new BSTree();

    public void readData() {
        try {
            Scanner sc = new Scanner(new File("ex01_input.txt"));
            n = sc.nextInt();
            a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
                tree.addNode(a[i]);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void solve() {

    }

    public void printReslut() {
        try {

            FileWriter fw = new FileWriter("ex01_output.txt ");
            tree.preOrder();
            fw.write(tree.getResult().substring(0, tree.getResult().length() - 1)+"\n");
            tree.postOrder();
            fw.write(tree.getResult().substring(0, tree.getResult().length() - 1));
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        BinarySearchTreeConsole b = new BinarySearchTreeConsole();
        b.readData();
        b.solve();
        b.printReslut();
    }

}

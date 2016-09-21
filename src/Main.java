
import java.lang.Math;
import java.text.DecimalFormat;

/**
 * Created by watariMac on 2016/08/03.
 */
public class Main {

    Record[] recordArray = new Record[20];

    static public void main(String args[]) {
        new Main(49.0, 0.2);


    }

    Main(double s0, double sigma) {
        recordArray[0] = new Record(s0, sigma);
        for (int i = 1; i < recordArray.length; i++) {
            double r1 = Math.random();
            double r2 = Math.random();

            if (i % 2 == 0) {
                recordArray[i] = new Record(recordArray[i - 1], calsGaussianCosValue(r1, r2), sigma);
            } else {
                recordArray[i] = new Record(recordArray[i - 1], calsGaussianSinValue(r1, r2), sigma);
            }

        }

       System.out.println(
                  String.format("%3s", "週")
                + String.format("%6s", "株価")
                + String.format("%6s", "デルタ")
                + String.format("%8s", "購入株式")
                + String.format("%8s", "購入コスト")
                + String.format("%8s", "累積コスト")
                + String.format("%8s", "金利コスト")
                + String.format("%12s", "コールオプション価格")
        );
        for (int i=0;i<recordArray.length;i++) {
            System.out.print(String.format("%3d ", i+1));
            System.out.println(recordArray[i]);
        }
        DecimalFormat df2 = new DecimalFormat("##0,000.0");
        System.out.println("------------------------------");

        System.out.println("銀行は" + df2.format(Record.stock) + "円*" + Record.k + "株=" + df2.format(Record.k * Record.stock));
        System.out.println("総コスト " + df2.format(recordArray[19].outstanding));
        System.out.println("銀行の損益は" + df2.format(Record.k * Record.stock - recordArray[19].outstanding));

    }


    //モンテカルロ法によるガウシアン乱数生成
    double calsGaussianCosValue(double r1, double r2) {
        return Math.sqrt(-2.0 * Math.log(r1)) * Math.cos(2.0 * Math.PI * r2);
    }

    double calsGaussianSinValue(double r1, double r2) {
        return Math.sqrt(-2.0 * Math.log(r1)) * Math.sin(2.0 * Math.PI * r2);
    }


}

import java.util.List;
import java.util.stream.IntStream;
import static java.lang.StrictMath.*;

public class TravelingSalesman {
    public static final int STOPS = 15;
    public static final int SCALE = 3;
    private static final double[][] ADJACENCE = matrix(STOPS);

    public static double[][] matrix(int stops) {
        final double radius = 100.0;
        double[][] matrix = new double[stops][stops];

        for (int i = 0; i < stops; ++i) {
            for (int j = 0; j < stops; ++j) {
                matrix[i][j] = chord(stops, abs(i - j), radius);
            }
        }
        showVertexesMatrix(matrix);
        return matrix;
    }

    public static double chord(int stops, int i, double r) {
        return 2.0 * r * abs(sin(PI * i / stops));
    }

    public static double dist(final int[] path) {
        return IntStream.range(0, STOPS)
                .mapToDouble(i -> ADJACENCE[path[i]][path[(i + 1) % STOPS]])
                .sum();
    }

    public static void showVertexesMatrix(double[][] edges) {
        int maxLength = 1;
        for (int i = 0; i < STOPS; i++)
            for (int j = 0; j < STOPS; j++)
                if (Double.toString(edges[i][j]).length() > maxLength)
                    maxLength = String.format("%."+ SCALE + "f",edges[i][j]).length();
        maxLength += 2;
        System.out.println("\n---ADJACENT MATRIX---\n");
        int amountOfUnderscore = String.valueOf(STOPS).length() + 1;
        StringBuilder underScores = new StringBuilder("");
        StringBuilder spaces = new StringBuilder("");
        for (int l = 0 ; l < amountOfUnderscore; l++) {
            underScores.append("_");
            spaces.append(" ");
        }
        StringBuilder titleRow = new StringBuilder(spaces.toString() + "|");
        StringBuilder subTitleRow = new StringBuilder("\n" + underScores + "|");
        for (int i = 0; i < STOPS; i++){
                String v = String.valueOf(i);
                int localLength = maxLength - v.length();
                int localLengthL = localLength / 2;
                int localLengthR = (localLength - localLengthL);
                for (int k = 0; k < localLengthL + 1; k++)
                    titleRow.append(" ");
                titleRow.append(String.format("%s", v));
                for (int k = 0; k < localLengthR; k++)
                    titleRow.append(" ");
                titleRow.append("|");
                for (int k = 0; k < maxLength + 1; k++)
                    subTitleRow.append("_");
                subTitleRow.append("|");
        }
        titleRow.append(subTitleRow);
        System.out.println(titleRow);
        for (int i = 0; i < STOPS; i++){
            String v = String.valueOf(i);


            StringBuilder titleColHelp = new StringBuilder("");
            int leftSymbolsTitleCol = amountOfUnderscore - v.length();
            for (int l = 0; l <  leftSymbolsTitleCol / 2; l++)
                titleColHelp.append(" ");
            titleColHelp.append(v);
            for (int l = 0; l <  (int)Math.round(leftSymbolsTitleCol / 2.0); l++)
                titleColHelp.append(" ");
            titleRow = new StringBuilder(titleColHelp + "|");
            subTitleRow = new StringBuilder(underScores.append("|").toString());
                for (int j = 0; j < STOPS; j++) {
                    String vJ = String.valueOf(j);
                    String value = "";
                    if (j != i)
                        value = String.format("%." + SCALE + "f", edges[i][j]);
                    else
                        value = "-";
                    int localLength = maxLength - value.length();
                    int localLengthL = localLength / 2;
                    int localLengthR = (localLength - localLengthL);
                    for (int k = 0; k < localLengthL; k++)
                        titleRow.append(" ");
                    titleRow.append(" ").append(value);
                    for (int k = 0; k < localLengthR; k++)
                        titleRow.append(" ");
                    titleRow.append("|");
                    for (int k = 0; k < maxLength + 1; k++)
                        subTitleRow.append("_");
                    subTitleRow.append("|");
                }
                underScores.deleteCharAt(underScores.length()-1);
                titleRow.append("\n").append(subTitleRow);
                System.out.println(titleRow);
        }
        System.out.println("\n");
    }
}

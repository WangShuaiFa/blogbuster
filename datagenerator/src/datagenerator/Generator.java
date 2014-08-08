package datagenerator;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Random;

public class Generator {

    private static Random random;

    public static void main(String[] args) {
        random = new Random();

        for (DataSize dataSize : DataSize.values()) {
            generateData(dataSize);
        }
    }

    private static void generateData(DataSize dataSize) {
        FileWriter studentsWritter;
        FileWriter ratingsWritter;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        try {
            String country = "Srbija";
            String separator = ",";

            studentsWritter = new FileWriter("students_" + dataSize.name() + ".txt");
            ratingsWritter = new FileWriter("ratings_" + dataSize.name() + ".txt");
            for (int i = 1; i <= dataSize.getSize(); i++) {
                String id = String.valueOf(i);
                String name = GeneratorData.names[random.nextInt(GeneratorData.names.length)];
                String lastName = GeneratorData.lastNames[random.nextInt(GeneratorData.lastNames.length)];
                String city = (i % 2 == 0) ? "Novi Sad" : "Beograd";
                String faculty = (i % 3 == 0) ? "PMF" : "FTN";
                String deparment = GeneratorData.departments[random.nextInt(GeneratorData.departments.length)];
                String rating = df.format(random.nextFloat() * (10f - 5f) + 5f);

                StringBuilder studentsSB = new StringBuilder();
                StringBuilder ratingsSB = new StringBuilder();

                studentsWritter.append(id);
                studentsWritter.append(separator);
                studentsWritter.append(name);
                studentsWritter.append(separator);
                studentsWritter.append(lastName);
                studentsWritter.append(separator);
                studentsWritter.append(country);
                studentsWritter.append(separator);
                studentsWritter.append(city);
                studentsWritter.append(separator);
                studentsWritter.append(faculty);
                studentsWritter.append(separator);
                studentsWritter.append(deparment);
                // studentsWritter.write(studentsSB.toString());
                if (i < dataSize.getSize()) {
                    studentsWritter.append("\n");
                }

                ratingsWritter.append(id);
                ratingsWritter.append(separator);
                ratingsWritter.append(rating);
                if (i < dataSize.getSize()) {
                    ratingsWritter.append("\n");
                }

            }
            studentsWritter.flush();
            ratingsWritter.flush();

            studentsWritter.close();
            ratingsWritter.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}

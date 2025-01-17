import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class App {
    final Color color = Color.RED;
    static int x = 800;
    static int y = 600;

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        Sorts sorts = new Sorts();

        int[] arr = new int[500];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100) + 1;
        }

        int[] currentIndex = {-1};

        JFrame frame = new JFrame("Sorting Demonstration");
        frame.setSize(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int barWidth = panelWidth / arr.length;

                for (int i = 0; i < arr.length; i++) {
                    int barHeight = (int) ((arr[i] / 100.0) * (panelHeight - 50));
                    int xPos = i * barWidth;
                    int yPos = panelHeight - barHeight;

                    if (i == currentIndex[0]) {
                        g.setColor(Color.BLUE);
                    } else {
                        g.setColor(Color.RED);
                    }

                    g.fillRect(xPos, yPos, barWidth - 2, barHeight); // Adjusted spacing for visibility
                }
            }
        };

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel sortLabel = new JLabel("Sorting Algorithm:");
        String[] sortTypes = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort"};
        JComboBox<String> sortBox = new JComboBox<>(sortTypes);

        JLabel delayLabel = new JLabel("Sorting Delay (ms):");
        String[] delayTypes = {"10ms", "50ms", "100ms", "200ms (default)"};
        JComboBox<String> delayBox = new JComboBox<>(delayTypes);
        delayBox.setSelectedIndex(3);

        topPanel.add(sortLabel);
        topPanel.add(sortBox);
        topPanel.add(delayLabel);
        topPanel.add(delayBox);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            String currentSort = (String) sortBox.getSelectedItem();
            String delayChoice = (String) delayBox.getSelectedItem();

            int delay = switch (delayChoice) {
                case "10ms" -> 10;
                case "50ms" -> 50;
                case "100ms" -> 100;
                default -> 200;
            };

            new Thread(() -> {
                switch (currentSort) {
                    case "Bubble Sort" -> sorts.bubbleSort(arr, panel, currentIndex, delay);
                    case "Selection Sort" -> sorts.selectionSort(arr, panel, currentIndex, delay);
                    case "Insertion Sort" -> sorts.insertionSort(arr, panel, currentIndex, delay);
                    case "Quick Sort" -> sorts.quickSort(arr, 0, arr.length - 1, panel, currentIndex, delay);
                    case "Merge Sort" -> sorts.mergeSort(arr, 0, arr.length-1, panel, delay, currentIndex);
                    default -> System.out.println("Unknown sorting algorithm.");
                }
            }).start();
        });
        buttonsPanel.add(startButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(100) + 1;
            }
            currentIndex[0] = -1;
            panel.repaint();
        });
        buttonsPanel.add(resetButton);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

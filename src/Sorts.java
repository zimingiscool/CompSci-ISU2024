import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Sorts {
    private final String soundFilePath = "C:\\Users\\canad\\OneDrive\\Documents\\CompSci - ISU\\CompSci-ISU\\src\\ping.wav";

    private final Clip soundClip;

    public Sorts() {
        Clip tempClip = null;
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFilePath))) {
            tempClip = AudioSystem.getClip();
            tempClip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Error initializing sound: " + e.getMessage());
        }
        soundClip = tempClip;
    }

    private void playSound() {
        if (soundClip != null) {
            new Thread(() -> {
                synchronized (soundClip) {
                    soundClip.setFramePosition(0);
                    soundClip.start();
                }
            }).start();
        }
    }

    public void bubbleSort(int[] arr, JPanel panel, int[] currentIndex, int delay) {
        new Thread(() -> {
            try {
                for (int i = 0; i < arr.length - 1; i++) {
                    for (int j = 0; j < arr.length - 1 - i; j++) {
                        if (arr[j] > arr[j + 1]) {
                            int temp = arr[j];
                            arr[j] = arr[j + 1];
                            arr[j + 1] = temp;
                            playSound();
                            currentIndex[0] = j + 1;
                            panel.repaint();
                            Thread.sleep(delay);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                currentIndex[0] = -1;
                panel.repaint();
            }
        }).start();
    }

    public void insertionSort(int[] arr, JPanel panel, int[] currentIndex, int delay) {
        new Thread(() -> {
            try {
                for (int i = 1; i < arr.length; i++) {
                    int key = arr[i];
                    int j = i - 1;
                    while (j >= 0 && arr[j] > key) {
                        arr[j + 1] = arr[j];
                        playSound();
                        j--;
                        currentIndex[0] = j + 1;
                        panel.repaint();
                        Thread.sleep(delay);
                    }
                    arr[j + 1] = key;
                    playSound();
                    currentIndex[0] = i;
                    panel.repaint();
                    Thread.sleep(delay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                currentIndex[0] = -1;
                panel.repaint();
            }
        }).start();
    }

    public void selectionSort(int[] arr, JPanel panel, int[] currentIndex, int delay) {
        new Thread(() -> {
            try {
                for (int i = 0; i < arr.length - 1; i++) {
                    int minIndex = i;
                    for (int j = i + 1; j < arr.length; j++) {
                        if (arr[j] < arr[minIndex]) {
                            minIndex = j;
                        }
                    }
                    if (minIndex != i) {
                        int temp = arr[minIndex];
                        arr[minIndex] = arr[i];
                        arr[i] = temp;
                        playSound();
                        currentIndex[0] = i;
                        panel.repaint();
                        Thread.sleep(delay);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                currentIndex[0] = -1;
                panel.repaint();
            }
        }).start();
    }

    public void quickSort(int[] arr, int low, int high, JPanel panel, int[] currentIndex, int delay) {
        if (low < high) {
            new Thread(() -> {
                try {
                    int pivotIndex = partition(arr, low, high, panel, currentIndex, delay);
                    quickSort(arr, low, pivotIndex - 1, panel, currentIndex, delay);
                    quickSort(arr, pivotIndex + 1, high, panel, currentIndex, delay);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    currentIndex[0] = -1;
                    panel.repaint();
                }
            }).start();
        }
    }

    private int partition(int[] arr, int low, int high, JPanel panel, int[] currentIndex, int delay) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                playSound();
                currentIndex[0] = j;
                panel.repaint();
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        playSound();
        currentIndex[0] = i + 1;
        panel.repaint();
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i + 1;
    }

    public void mergeSort(int[] arr, int left, int right, JPanel panel, int delay, int[] currentIndex) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid, panel, delay, currentIndex);
            mergeSort(arr, mid + 1, right, panel, delay, currentIndex);
            merge(arr, left, mid, right, panel, delay, currentIndex);
        }
    }

    private void merge(int[] arr, int left, int mid, int right, JPanel panel, int delay, int[] currentIndex) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            try {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i++;
                } else {
                    arr[k] = R[j];
                    j++;
                }
                playSound();
                currentIndex[0] = k;
                panel.repaint();
                Thread.sleep(delay);
                k++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        while (i < n1) {
            arr[k] = L[i];
            playSound();
            currentIndex[0] = k;
            panel.repaint();
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            playSound();
            currentIndex[0] = k;
            panel.repaint();
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
            j++;
            k++;
        }

        currentIndex[0] = -1;
        panel.repaint();
    }
}

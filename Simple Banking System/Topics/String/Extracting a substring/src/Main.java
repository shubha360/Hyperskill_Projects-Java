class Main {
    public static void main(String[] args) {
        // put your code here
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine();
        int start = scanner.nextInt();
        int end = scanner.nextInt();

        System.out.println(input.substring(start, end + 1));
    }
}
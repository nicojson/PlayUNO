import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido a UNO!");
        System.out.println("Seleccione el modo de juego:");
        System.out.println("1. Local (Dos jugadores)");
        System.out.println("2. Contra la IA");

        int opcion;
        do {
            System.out.print("Ingrese su opción (1 o 2): ");
            opcion = scanner.nextInt();
        } while (opcion != 1 && opcion != 2);

        if (opcion == 1) {
            jugarModoLocal();
        } else {
            jugarContraIA();
        }

        scanner.close();
    }

    public static void jugarModoLocal() {
        Scanner scanner = new Scanner(System.in);
        List<String> mazo = generarMazo();
        Collections.shuffle(mazo);

        List<String> jugador1 = new ArrayList<>(mazo.subList(0, 7));
        List<String> jugador2 = new ArrayList<>(mazo.subList(7, 14));
        mazo = new ArrayList<>(mazo.subList(14, mazo.size()));

        String cartaActual;
        do {
            cartaActual = mazo.remove(0);
        } while (cartaActual.startsWith("+") || cartaActual.equals("Uno No Mercy") || cartaActual.equals("Cambio de Color"));

        int turno = 1;
        boolean cambioDeColor = false;

        System.out.println("Modo Local iniciado. Cada jugador tiene 7 cartas.");

        while (true) {
            System.out.println("\nTurno del Jugador " + turno);
            System.out.println("Carta en juego: " + cartaActual);
            List<String> mano = (turno == 1) ? jugador1 : jugador2;

            while (true) {
                System.out.println("Tus cartas:");
                for (int i = 0; i < mano.size(); i++) {
                    System.out.println((i + 1) + ". " + mano.get(i));
                }
                System.out.print("Elige una carta para jugar o ingresa 0 para robar: ");
                int eleccion = scanner.nextInt();

                if (eleccion == 0) {
                    while (true) {
                        if (!mazo.isEmpty()) {
                            String nuevaCarta = mazo.remove(0);
                            mano.add(nuevaCarta);
                            System.out.println("Has robado: " + nuevaCarta);

                            if (puedeJugar(nuevaCarta, cartaActual)) {
                                System.out.println("Puedes jugar esta carta si lo deseas.");
                                break;
                            }

                            System.out.print("¿Quieres seguir robando? (s/n): ");
                            String respuesta = scanner.next();
                            if (respuesta.equalsIgnoreCase("n")) {
                                break;
                            }
                        } else {
                            System.out.println("No hay más cartas en el mazo.");
                            break;
                        }
                    }
                } else if (eleccion > 0 && eleccion <= mano.size()) {
                    String seleccionada = mano.get(eleccion - 1);
                    if (puedeJugar(seleccionada, cartaActual) || cambioDeColor) {
                        cartaActual = mano.remove(eleccion - 1);
                        System.out.println("Has jugado: " + cartaActual);
                        cambioDeColor = false;

                        if (mano.isEmpty()) {
                            System.out.println("¡Jugador " + turno + " ha ganado!");
                            return;
                        }

                        if (seleccionada.contains("Cancelar Turno")) {
                            System.out.println("El turno del oponente ha sido cancelado.");
                            continue;
                        } else if (seleccionada.equals("Cambio de Color") || seleccionada.startsWith("+4") || seleccionada.startsWith("+6") || seleccionada.startsWith("+10")) {
                            System.out.print("Elige un nuevo color (Rojo, Azul, Verde, Amarillo): ");
                            String nuevoColor = scanner.next();
                            cartaActual = nuevoColor + " X";
                            cambioDeColor = true;
                        }
                        break;
                    } else {
                        System.out.println("No puedes jugar esa carta. Debes jugar una carta válida.");
                    }
                } else {
                    System.out.println("Movimiento no válido.");
                }
            }
            turno = (turno == 1) ? 2 : 1;
        }
    }

    public static boolean puedeJugar(String carta, String cartaActual) {
        if (carta.equals("Cambio de Color") || carta.equals("Uno No Mercy") || carta.startsWith("+")) {
            return true;
        }
        String[] partesCarta = carta.split(" ");
        String[] partesCartaActual = cartaActual.split(" ");
        return partesCarta[0].equals(partesCartaActual[0]) || partesCarta[1].equals(partesCartaActual[1]);
    }

    public static List<String> generarMazo() {
        List<String> mazo = new ArrayList<>();
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        String[] numeros = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (String color : colores) {
            for (String numero : numeros) {
                mazo.add(color + " " + numero);
                mazo.add(color + " " + numero);
            }
        }
        for (int i = 0; i < 3; i++) mazo.add("+4");
        for (int i = 0; i < 2; i++) mazo.add("+6");
        for (int i = 0; i < 2; i++) mazo.add("+10");
        for (int i = 0; i < 3; i++) mazo.add("Uno No Mercy");
        for (int i = 0; i < 4; i++) mazo.add("Cambio de Color");
        return mazo;
    }

    public static void jugarContraIA() {
        System.out.println("Modo IA en desarrollo...");
    }
}

//
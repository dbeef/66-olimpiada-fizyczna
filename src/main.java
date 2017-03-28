import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {

	// Daniel Zalega OF66-I-283
	// Zadanie T4 (numeryczne)

	static final int X1 = 100;
	static final int Y1 = -1;

	static final double BETA_JEDEN = 0;
	static final double BETA_DWA = 0.0001f;
	static final double BETA_TRZY = 0.001f;
	static final double BETA_CZTERY = 0.01f;

	static double wybrane_tarcie;

	static double czas_ruchu_po_prostej = 0;
	static double wspolczynnik_a_prostej = 0;

	static double wspolczynnik_funkcji_najmniejszego_czasu_a = 0;
	static double wspolczynnik_funkcji_najmniejszego_czasu_b = 0;
	static double droga_funkcji_najmniejszego_czasu = 0;
	static double najmniejszy_czas = Double.MAX_VALUE;

	public static void main(String[] args) throws IOException {

		double wspolczynnik_a = 0;
		double wspolczynnik_a_KONCOWY = 0;

		System.out.println("Daniel Zalega OF66-I-283");
		System.out.println("Podaj poczatkowy wspolczynnik a: (zalecany '-1')");
		wspolczynnik_a = odczytaj_z_klawiatury();
		System.out.println("Podaj koncowy wspolczynnik a: (zalecany '1')");
		wspolczynnik_a_KONCOWY = odczytaj_z_klawiatury();
		System.out.println("Obliczac tarcie beta 1, beta 2, beta 3, czy beta4? Wpisz 1, 2,3 lub 4.");
		int ktore_tarcie = odczytaj_z_klawiatury();

		if (ktore_tarcie == 1)
			wybrane_tarcie = BETA_JEDEN;
		if (ktore_tarcie == 2)
			wybrane_tarcie = BETA_DWA;
		if (ktore_tarcie == 3)
			wybrane_tarcie = BETA_TRZY;
		if (ktore_tarcie == 4)
			wybrane_tarcie = BETA_CZTERY;

		while (wspolczynnik_a < wspolczynnik_a_KONCOWY) {

			double wspolczynnik_b = 0;
			boolean czy_funkcja_liniowa = false;

			wspolczynnik_a += 0.001f;
			wspolczynnik_b = (Y1 - (wspolczynnik_a * (X1 * X1))) / X1;

			if (isZero(wspolczynnik_a, 0.01f) == true)
				czy_funkcja_liniowa = true;

			oblicz_calkowity_czas_dla_funkcji(wspolczynnik_a, wspolczynnik_b, czy_funkcja_liniowa);
		}

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Skonczone. Najmniejszy czas to: " + najmniejszy_czas + " sekund.");
		System.out.println("Dla rownania:");
		System.out.print("y = (");
		System.out.printf("%.5f", wspolczynnik_funkcji_najmniejszego_czasu_a);
		System.out.print(")*x^2+(");
		System.out.printf("%.5f", wspolczynnik_funkcji_najmniejszego_czasu_b);
		System.out.print(")x");
		System.out.println();
		System.out.println("I tarcia beta " + ktore_tarcie);
		System.out.println("Calkowita droga dla niej to: " + droga_funkcji_najmniejszego_czasu);
		System.out.println();
		System.out.println("Czas ruchu po prostej wynosi: " + czas_ruchu_po_prostej);
		System.out.println("Wspolczynnik 'a' prostej wynosi: " + wspolczynnik_a_prostej);

		System.out.println();
		System.out.println("Wcisnij enter aby zakonczyc dzialanie programu.");
		odczytaj_z_klawiatury();
	}

	// Funkcja do odczytywania danych z klawiatury, nieistotne z perspektywy
	// samego algorytmu
	public static int odczytaj_z_klawiatury() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			int i = Integer.parseInt(br.readLine());
			return i;
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid Format!");
			return 0;
		}
	}

	public static void oblicz_calkowity_czas_dla_funkcji(double a, double b, boolean czy_funkcja_liniowa) {

		double calkowita_droga = 0;

		double predkosc = 0;

		double x = 0;
		double y = 0;

		double y_w_poprzednim_punkcie = 0;

		double delta_x = 0.0001f;

		double calkowity_czas = 0;

		while (x < X1) {

			x += delta_x;

			if (x > X1) {
				delta_x = x - X1;
				x = X1;
			}

			y = (a * x * x + b * x);

			if (x == delta_x && y > 0) {
				System.out.println("Wznosi sie ponad zero, przerywam petle.");
				break;
			}

			double pochodna = (2 * a * x) + b;
			double delta_Y = Math.abs(y) - Math.abs(y_w_poprzednim_punkcie);
			double delta_S = 0;

			if (czy_funkcja_liniowa == false)
				delta_S = Math.sqrt(1 + (pochodna * pochodna)) * delta_x;
			else {
				delta_S = Math.sqrt(delta_x * delta_x + delta_Y * delta_Y);
			}

			predkosc = Math.sqrt((2 * 10 * delta_Y) + (predkosc * predkosc * (1 - 2 * wybrane_tarcie * delta_S)));

			if (predkosc < 0) {
				System.out.println("Predkosc mniejsza od zera, przerywam petle.");
				break;
			}

			double przyrost_czasu = delta_S / predkosc;

			calkowity_czas += przyrost_czasu;
			y_w_poprzednim_punkcie = y;
			calkowita_droga += delta_S;
		}

		if (calkowity_czas > 0) {
			// Jesli calkowity czas jest wiekszy od zera, to wypisujemy
			// charakterystyke
			System.out.println();
			System.out.println("Czas ruchu to" + calkowity_czas + " sekund");
			System.out.println("Dla rownania:");
			System.out.print("y = (");
			System.out.printf("%.5f", a);
			System.out.print(")*x^2+(");
			System.out.printf("%.5f", b);
			System.out.print(")x");
			System.out.println();
			System.out.println("Calkowita droga to " + calkowita_droga);
			if (czy_funkcja_liniowa == true) {
				// Jesli jeszcze nie otrzymalismy czasu ruchu dla prostej
				// (wartosc zmiennej jest rowna 0) i mamy teraz do czynienia z
				// funkcja liniowa, to wstawiamy jej wartosc do zmiennej.
				// Komputer moze okreslic czy ulamek jest rowny zero z pewnym
				// bledem pomiarowym (wynika to z reprezentacji ulamkow
				// binarnie), dlatego dla bardzo malych ulamkow moze
				// stwierdzic
				// ze sa rowne 0, mimo ze nie sa!
				// Dlatego wlasnie zapisuje czas dla pierwszej funkcji liniowej
				// jaka sie pojawi i ignoruje nastepne
				if (czas_ruchu_po_prostej == 0) {
					czas_ruchu_po_prostej = calkowity_czas;
					wspolczynnik_a_prostej = b;
				}
			}
			System.out.println();
			if (calkowity_czas < najmniejszy_czas) {
				najmniejszy_czas = calkowity_czas;
				wspolczynnik_funkcji_najmniejszego_czasu_a = a;
				wspolczynnik_funkcji_najmniejszego_czasu_b = b;
				droga_funkcji_najmniejszego_czasu = calkowita_droga;
			}
		} else {
			System.out.println();
			System.out.println("Kulka nie doleci.");
			System.out.println("Dla rownania:");
			System.out.print("y = (");
			System.out.printf("%.5f", a);
			System.out.print(")*x^2+(");
			System.out.printf("%.5f", b);
			System.out.print(")x");
			System.out.println();
		}
	}

	// Funkcja sprawdza czy dana liczba zmiennoprzecinkowa jest rowna 0
	static public boolean isZero(double value, double threshold) {
		return value >= -threshold && value <= threshold;
	}
}
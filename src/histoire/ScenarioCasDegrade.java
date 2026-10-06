package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		// 1) libererEtal sur un étal jamais occupé (NullPointerException gérée dans Etal)
		Etal etal = new Etal();
		etal.libererEtal();

		// 2a) acheteur null (NullPointerException gérée dans Etal)
		etal.occuperEtal(new Gaulois("Bonemine", 7), "fleurs", 20);
		System.out.println(etal.acheterProduit(5, null));

		// 2b) quantité non positive (IllegalArgumentException)
		try {
			etal.acheterProduit(-1, new Gaulois("Obélix", 25));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		// 2c) étal non occupé (IllegalStateException)
		Etal etalVide = new Etal();
		try {
			etalVide.acheterProduit(3, new Gaulois("Obélix", 25));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		System.out.println("Fin du test");
	}
}
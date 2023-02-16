package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marché;
	
	private static class Marche{
		private Etal[] etals;
		private int nbEtals;
		private Marche(int nbEtals) {
			this.nbEtals = nbEtals;
			this.etals = new Etal[nbEtals];
			for (int i = 0; i<nbEtals ; i++) {
				etals[i] = new Etal();
			}
		}
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		private int trouverEtalLibre() {
			boolean found = true;
			int i = 0;
			while ((found==true)&&(i<nbEtals)) {
				found = etals[i].isEtalOccupe();
				if (found==true) {
					i++;
				}
			}
			if (found==true) {
				i = -1;
			}
			return i;
		}
		private Etal[] trouverEtals(String produit) {
			int i = 0;
			int j = 0;
			for (i = 0 ; i<nbEtals ; i++) {
				if((etals[i].isEtalOccupe()))
				{
					if(etals[i].contientProduit(produit)) {
					j++;
				}
				}
			}
			Etal[] etals_concernes = new Etal[j];
			for (int k = 0; k<j ; k++) {
				etals_concernes[k] = new Etal();
			}
			j = 0;
			for (i = 0 ; i<nbEtals ; i++) {
				if (etals[i].isEtalOccupe())
				{
				if(etals[i].contientProduit(produit)) {
					etals_concernes[j] = etals[i];
					j++;
				}
				}
			}
			System.out.println("Test : ");
			return etals_concernes;
		}
		private Etal trouverVendeur(Gaulois gaulois) {
			int i = 0;
			while (i<nbEtals) {
				if ((etals[i].getVendeur().getNom()).equals(gaulois.getNom()))
				{
					return etals[i];
				}
				i++;
			}
			return null;
		}
		private String afficherMarche() {
			int i = 0;
			int nbEtalsVides = 0;
			String affichage = "";
			for(i=0 ; i<nbEtals ; i++) {
				if (etals[i].isEtalOccupe()) {
					affichage = affichage + etals[i].afficherEtal() + "\n";
				}
				else {
					nbEtalsVides ++;
				}
			}
			if (nbEtalsVides > 0) {
				affichage = affichage + "Il reste " + nbEtalsVides + " étals vides sur le marché.";
			}
			return affichage;
		}
		
	}
	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marché = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbproduit) {
		StringBuilder chaine = new StringBuilder();
		int indice = 0;
		indice = marché.trouverEtalLibre();
		chaine.append(vendeur.getNom() + " cherche un étal pour vendre " + nbproduit + " " + produit + "\n");
		if (indice!=-1) {
			marché.utiliserEtal(indice, vendeur, produit, nbproduit);
			chaine.append(vendeur.getNom() + " s'installe à l'étal numéro " + indice);
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui vendent du "+ produit + " sont :\n");
		Etal[] etals_concernes = marché.trouverEtals(produit);
		System.out.println(etals_concernes.length);
		for (int i=0 ; i<etals_concernes.length ; i++) {
			chaine.append("- " + etals_concernes[i].getVendeur().getNom() + "\n");
		}
		return chaine.toString();
	}
}
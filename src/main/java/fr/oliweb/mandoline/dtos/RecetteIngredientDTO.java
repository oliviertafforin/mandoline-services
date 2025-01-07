package fr.oliweb.mandoline.dtos;

public class RecetteIngredientDTO {

    private RecetteDTO recette;

    private IngredientDTO ingredient;

    private int quantite;

    private String unite;

    private Boolean optionnel;

    public RecetteDTO getRecette() {
        return recette;
    }

    public void setRecette(RecetteDTO recette) {
        this.recette = recette;
    }

    public IngredientDTO getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientDTO ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Boolean getOptionnel() {
        return optionnel;
    }

    public void setOptionnel(Boolean optionnel) {
        this.optionnel = optionnel;
    }
}

package me.aifusp.foodx;

public enum InventoryName {

    MAIN_MENU("FoodX Menu", 9),
    FOOD_LIST("Foodx FoodList",45),
    EDITABLE_FOOD_LIST("Foodx List Editor",45),
    FOOD_EDITOR("Foodx Editor",9),

    FOOD_BUILDER("Foodx Builder",9);

    private final String menuName;
    private final int size;

        private InventoryName(String name, int size) {
        this.menuName = name;
        this.size = size;
    }

    public String getName() {
        return this.menuName;
    }

    public int getSize() {
        return this.size;
    }

}
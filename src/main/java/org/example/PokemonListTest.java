package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;

public class PokemonListTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_National_Pok%C3%A9dex_number");

            List<Pokemon> pokemons = new ArrayList<>();
            List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@class, 'roundy')]/tbody/tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() >= 4) {
                    String idText = cells.get(0).getText().replace("#", "").trim();
                    int id;
                    try {
                        id = Integer.parseInt(idText);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    String href = cells.get(1).findElement(By.tagName("a")).getAttribute("href");
                    String name = cells.get(2).findElement(By.tagName("a")).getText();
                    String type1 = cells.get(3).getText();
                    String type2 = cells.size() > 4 ? cells.get(4).getText() : "";

                    Pokemon pokemon = new Pokemon(id, name, href, type1, type2, "");
                    pokemons.add(pokemon);
                }
            }

            for (Pokemon pokemon : pokemons) {
                driver.get(pokemon.getHref());

                List<WebElement> eggGroupElements = driver.findElements(By.xpath("//a[contains(@href, '/wiki/Egg_Group')]/ancestor::td[1]//table[@class='roundy']//td"));
                StringBuilder eggGroups = new StringBuilder();
                for (WebElement element : eggGroupElements) {
                    if (!eggGroups.isEmpty()) {
                        eggGroups.append(", ");
                    }
                    eggGroups.append(element.getText());
                }

                pokemon.setEggGroups(eggGroups.toString());

                System.out.println(pokemon);
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}

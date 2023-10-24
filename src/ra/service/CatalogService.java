package ra.service;

import ra.model.Catalog;
import ra.model.Product;
import ra.util.DataBase;

import java.util.ArrayList;
import java.util.List;

public class CatalogService implements IGenericService<Catalog, Integer> {
    private List<Catalog> catalogs;
    private CartService cartService;
    private DataBase<Catalog> catalogData = new DataBase();

    public CatalogService() {
        List<Catalog> list = catalogData.readFromFile(DataBase.CATALOG_PATH);
        if (list == null) {
            list = new ArrayList<>();
        }
        this.catalogs = list;
    }

    @Override
    public List<Catalog> findAll() {
        return catalogs;
    }

    @Override
    public void save(Catalog catalog) {
        if (findById(catalog.getId()) == null) {
            // add
            catalogs.add(catalog);
        } else {
            // update
            catalogs.set(catalogs.indexOf(findById(catalog.getId())), catalog);
        }
        // luu vao file
        catalogData.writeToFile(catalogs, DataBase.CATALOG_PATH);
    }

    @Override
    public void delete(Integer id) {
        catalogs.remove(findById(id));
        catalogData.writeToFile(catalogs, DataBase.CATALOG_PATH);
    }

    @Override
    public Catalog findById(Integer id) {
        for (Catalog c : catalogs) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Catalog> searchByName(String searchName) {
        List<Catalog> catalogListSearch = new ArrayList<>();
        for (Catalog catalog : catalogs) {
            if (catalog.getName().toLowerCase().contains(searchName.toLowerCase())) {
                catalogListSearch.add(catalog);
            }
        }
        return catalogListSearch;
    }
    public int getNewId() {
        int max = 0;
        for (Catalog c : catalogs) {
            if (c.getId() > max) {
                max = c.getId();
            }
        }
        return max + 1;
    }
}


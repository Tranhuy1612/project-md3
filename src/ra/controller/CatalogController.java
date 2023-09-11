package ra.controller;

import ra.model.CartItem;
import ra.model.Catalog;
import ra.service.CatalogService;

import java.util.List;

public class CatalogController {
    private CatalogService catalogService;

    public CatalogController() {
        catalogService = new CatalogService();
    }

    public List<Catalog> findAll() {
        return catalogService.findAll();
    }

    public void save(Catalog catalog) {
        catalogService.save(catalog);
    }

    public void delete(Integer id) {
        catalogService.delete(id);
    }

    public Catalog findById(Integer id) {
        return catalogService.findById(id);
    }
    public List<Catalog> searchByName(String searchName) {
        return catalogService.searchByName(searchName);
    }
}

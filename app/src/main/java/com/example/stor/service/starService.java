package com.example.stor.service;

import com.example.stor.beans.Star;
import com.example.stor.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class StarService implements IDao<Star> {
    private List<Star> stars;
    private static StarService instance;

    private StarService() {
        stars = new ArrayList<>();
        seed();
    }

    public static StarService getInstance() {
        if (instance == null) instance = new StarService();
        return instance;
    }

    private void seed() {
        stars.add(new Star("Emma Watson", "https://i.imgur.com/EmWat.jpg", 4.5f));
        stars.add(new Star("Tom Cruise", "https://i.imgur.com/TomCr.jpg", 4.2f));
        stars.add(new Star("Scarlett Johansson", "https://i.imgur.com/ScarJo.jpg", 4.7f));
        stars.add(new Star("Leonardo DiCaprio", "https://i.imgur.com/LeoD.jpg", 4.8f));
    }

    @Override
    public boolean create(Star o) {
        return stars.add(o);
    }

    @Override
    public boolean update(Star o) {
        for (Star s : stars) {
            if (s.getId() == o.getId()) {
                s.setName(o.getName());
                s.setImg(o.getImg());
                s.setRating(o.getRating());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Star o) {
        return stars.remove(o);
    }

    @Override
    public Star findById(int id) {
        for (Star s : stars)
            if (s.getId() == id) return s;
        return null;
    }

    @Override
    public List<Star> findAll() {
        return stars;
    }
}

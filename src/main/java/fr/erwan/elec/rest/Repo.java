package fr.erwan.elec.rest;
import java.util.Optional;
import java.util.List;

public interface Repo {
    public List<Model> getData();
    public Optional<Model> getDataById(long id);
    public Optional<Model> getDataByDate(String date);
    public boolean save(ModelFront model);
    public boolean update(ModelFront model);
    public boolean delete(long id);
}

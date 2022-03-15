package ir.maktab.firstspringboot.service;


import ir.maktab.firstspringboot.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public abstract class BaseService<T, ID> {

    private JpaRepository<T, ID> jpaRepository;

    public void setJpaRepository(JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Transactional
    public T save(T entity) {
        return jpaRepository.save(entity);
    }

    @Transactional
    public T update(T entity) {
        return jpaRepository.save(entity);
    }

    public T loadById(ID id) {
        return jpaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Entity", "ID", id)
        );
    }

    public List<T> loadAll() {
        return jpaRepository.findAll();
    }

    public void deleteById(ID id) {
        jpaRepository.deleteById(id);
    }
}
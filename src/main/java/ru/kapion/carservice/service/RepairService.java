package ru.kapion.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kapion.carservice.dao.specific.RepairRepository;
import ru.kapion.carservice.model.Repair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class RepairService {
    @Autowired
    private RepairRepository repository;

    public void save(Repair repair) {
        repository.save(repair);
    }
    public void delete(Integer id) {
        repository.delete(getById(id));
    }

    public List<Repair> getAll() {
        return repository.getAll();
    }


    //Сумма по столбцу cost
    public BigDecimal getAllSum() {
        List<BigDecimal> costList = new ArrayList<>();
        Function<Repair, BigDecimal> totalMapper = r -> r.getCost().plus();

        BigDecimal result = getAll().stream()
                .map(totalMapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return result;
    }


//    public List<Repair> getAll() {
//        return StreamSupport
//                .stream(
//                        Spliterators.spliteratorUnknownSize(repository.findAll().iterator(), Spliterator.NONNULL),
//                        false)
//                .sorted(Comparator.reverseOrder())
//                .collect(Collectors.toList());
//    }

    public Repair getById(Integer id) {
        return repository.findById(id).orElse(new Repair());
    }
}

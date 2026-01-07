package com.rentalcar.repository;

import com.rentalcar.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    //8. Tìm xe theo id
    List<Car> findByCarId(Long cardId);

    //2. Tìm theo tên
    List<Car> findByModelName(String modelName);

    //3. Tìm theo loại xe
    List<Car> findByTypeId(Long typeId);

    //4. Tìm theo hãng xe
    List<Car> findByBrandId(Long brandId);

    //5. Tìm xe theo giá

    //6. Tìm xe theo khoảng thời gian

    //7. Tìm xe theo trạng thái
    List<Car> findByStatus(String status);

}

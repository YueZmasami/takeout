package com.yueZhai.reggie.dto;

import com.yueZhai.reggie.entity.Setmeal;
import com.yueZhai.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.Student;

public interface StudentMapper {
    @Select("select id, description, json_info as info from student where id = #{id};")
    Student getStudentById(Integer id);

    @Update("update student set json_info = #{info} where id = #{id};")
    Integer UpdateStudentInfo(@Param("id") Integer id, @Param("info") String info);
}

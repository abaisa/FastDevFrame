import dao.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pojo.Student;

import java.io.IOException;
import java.io.InputStream;

/*
 todo SqlSession 不是线程安全的！然后你懂的！
 */

public class MyBatisTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    @Test
    public void selectBySql() throws IOException{
        System.out.println("start test");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Student demo =  session.selectOne("demo.DemoTableMapper.demoSelect", 1);
            System.out.println(demo);
        } finally {
            session.close();
        }

        System.out.println("finish test");
    }

    @Test
    public void testSelectByInterfaceAnnotation() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            StudentMapper mapper = openSession.getMapper(StudentMapper.class);
            Student s = mapper.getStudentById(2);
            System.out.println(s);
        }finally {
            openSession.close();
        }
    }

    /*
    todo uodate 这种操作都是事务性的！！要记得commit！！不commit自动回滚的！可以校验一下affect rows
    */
    @Test
    public void testUpdateStudentExtra() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            StudentMapper mapper = openSession.getMapper(StudentMapper.class);
            Integer affectRows = mapper.UpdateStudentInfo(2, "updateBy  Javas");
            System.out.println(">>>>>>>>>>>>>>>>>");
            System.out.println(affectRows);
            System.out.println(">>>>>>>>>>>>>>>>>");
            openSession.commit();
        }finally {
            openSession.close();
        }
    }
}

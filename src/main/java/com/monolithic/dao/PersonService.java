package com.monolithic.dao;

import com.monolithic.entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Created by bonismo@hotmail.com on 2019/7/25 11:07 AM
 * @Description:
 * @Version: 1.0
 */
@Service
@Transactional("matchingTransaction")
public class PersonService {

    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }


    public void createPerson(Person person) {
        personDao.insertPerson(person);
        // Test double datasource transaction
         int i = 1 / 0;
    }

    public void updatePerson(Person person) {
        personDao.update(person);
    }

    public Person findPerson(String name) {
        return personDao.findPersonByName(name);
    }

    public void deletePerson(Long id) {
        personDao.delete(id);
    }
}

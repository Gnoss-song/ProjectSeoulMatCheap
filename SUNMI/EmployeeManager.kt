package Employee

import java.util.*
import kotlin.random.Random

enum class Grade { RAGULAR, PART_TIME, SALESMAN }
enum class Department(val test : String) {
    DEVELOPMENT("DEVELOPMENT"), CLIENT_SERVICE("CLIENT_SERVICE"), OFFICE_SERVICE("OFFICE_SERVICE"), SALES("SALES")
}
data class Employee(val id : String, var grade: Grade, var department: Department, var salary : Int)

interface Depart {
    fun getDepartment(grade: Grade): Department
    fun getSalary(grade: Grade, condition: Int): Int
}

class DepartImpl : Depart {

    companion object {
        const val BASIC_ANNUAL_SALARY = 120_000_000     //연봉
        const val HOURLY_WAGE = 25000                   //시급
    }

    override fun getDepartment(grade: Grade) = when(grade) {
        Grade.SALESMAN -> Department.SALES
        Grade.RAGULAR -> Department.values()[Random.nextInt(0, 4)]
        Grade.PART_TIME -> Department.values()[Random.nextInt(0, 3)]
    }

    /**
     * RAGULAR (정규직, condition= 0)
     * PART_TIME (파트타임, condition=시간)
     * SALESMAN (영업직, condition=영업실적)
     */
    override fun getSalary(grade: Grade, condition: Int) = when(grade) {
        Grade.RAGULAR -> BASIC_ANNUAL_SALARY/13
        Grade.PART_TIME -> condition * HOURLY_WAGE
        Grade.SALESMAN -> BASIC_ANNUAL_SALARY/13 + condition*0.05
    }.toInt()

}

class EmployeeGenerator(depart: Depart) : Depart by depart {

    val memberList = arrayListOf<Employee>()
    val developGroup = arrayListOf<Employee>()
    val salesGroup = arrayListOf<Employee>()
    val clientGroup = arrayListOf<Employee>()
    val officeGroup = arrayListOf<Employee>()

    companion object {
        //사번 생성 함수
        fun getId () = UUID.randomUUID().toString()
    }

    // 정규직 사원 생성 함수
    fun generateRegulars(people : Int) : List<Employee> {
        for(i in 0..(people-1)) {
            val employee = Employee(getId(), Grade.RAGULAR, getDepartment(Grade.RAGULAR), getSalary(Grade.RAGULAR, 0))
            memberList.add(employee)
            addGropList(employee.department, employee)
        }
        return memberList
    }

    // 파트타임 사원 생성 함수
    fun generatePartTimes(people: Int, hour: Int) : List<Employee> {
        for(i in 0..(people-1)) {
            val employee = Employee(getId(), Grade.PART_TIME, getDepartment(Grade.PART_TIME), getSalary(Grade.PART_TIME, hour))
            memberList.add(employee)
            addGropList(employee.department, employee)
        }
        return memberList
    }

    // 영업사원 생성 함수
    fun gerateSalesMans(people: Int, score: Int) : List<Employee> {
        for(i in 0..(people-1)) {
            val employee = Employee(getId(), Grade.SALESMAN, getDepartment(Grade.SALESMAN), getSalary(Grade.SALESMAN, score))
            memberList.add(employee)
            addGropList(employee.department, employee)
        }
        return memberList
    }

    //부서 리스트에 추가
    fun addGropList(department: Department, employee: Employee) {
        when(department) {
            Department.DEVELOPMENT -> developGroup.add(employee)
            Department.SALES -> salesGroup.add(employee)
            Department.CLIENT_SERVICE -> clientGroup.add(employee)
            Department.OFFICE_SERVICE -> officeGroup.add(employee)
        }
    }

}

//사원들을 관리하는 employee manager
class EmployeeManager(val memberList : List<Employee>) {

    fun getTotalSalary() : Int {
        var total = 0
        for (i in 0..memberList.lastIndex) {
            total += memberList[i].salary
        }
        return total
    }

    fun getAverageSalary () = getTotalSalary()/memberList.size

    fun getTotalDepartSalary (depart: Department) : Int {
        var total = 0
        for (i in 0..memberList.lastIndex) {
            if(memberList[i].department == depart)
                total += memberList[i].salary
        }
        return total
    }

    fun getAverageDepartSalary (depart: Department) : Int {
        var count = 0
        for (i in 0..memberList.lastIndex) {
            if(memberList[i].department == depart)
                count++
        }
        return getTotalDepartSalary(depart)/count
    }
}

//사원들을 관리하는 employee manager
class EmployeeManager2 {

    fun getTotalSalary(memberList: List<Employee>) : Int {
        var total = 0
        for (i in 0..memberList.lastIndex) {
            total += memberList[i].salary
        }
        return total
    }

    fun getAverageSalary (memberList: List<Employee>) = getTotalSalary(memberList)/memberList.size
}

//실행
fun main() {

    //부서생성
    val depart = DepartImpl()

    //사원생성
    val employeeGenerator = EmployeeGenerator(depart)
    employeeGenerator.generateRegulars(5)
    employeeGenerator.generatePartTimes(5, 6)
    employeeGenerator.gerateSalesMans(5, 500000)

    //임금계산
    val employeeManager = EmployeeManager(employeeGenerator.memberList)
    println("전체 : ${employeeManager.getTotalSalary()}")
    println("평균 : ${employeeManager.getTotalSalary()}")
    println("영업 : ${employeeManager.getTotalDepartSalary(Department.SALES)}")
    println("평균 : ${employeeManager.getAverageDepartSalary(Department.SALES)}")
    println("개발 : ${employeeManager.getTotalDepartSalary(Department.DEVELOPMENT)}")
    println("평균 : ${employeeManager.getAverageDepartSalary(Department.DEVELOPMENT)}")
//    println("사무 : ${employeeManager.getTotalDepartSalary(Department.OFFICE_SERVICE)}")
//    println("평균 : ${employeeManager.getAverageDepartSalary(Department.OFFICE_SERVICE)}")
//    println("고객 : ${employeeManager.getTotalDepartSalary(Department.CLIENT_SERVICE)}")
//    println("평균 : ${employeeManager.getAverageDepartSalary(Department.CLIENT_SERVICE)}")

    val employeeManager2 = EmployeeManager2()
    println("전체 : ${employeeManager2.getTotalSalary(employeeGenerator.memberList)}")
    println("평균 : ${employeeManager2.getTotalSalary(employeeGenerator.memberList)}")
    println(employeeGenerator.memberList.size)
    println("영업 : ${employeeManager2.getTotalSalary(employeeGenerator.salesGroup)}")
    println("평균 : ${employeeManager2.getTotalSalary(employeeGenerator.salesGroup)}")
    println(employeeGenerator.salesGroup.size)
    println("개발 : ${employeeManager2.getTotalSalary(employeeGenerator.developGroup)}")
    println("평균 : ${employeeManager2.getTotalSalary(employeeGenerator.developGroup)}")
    println(employeeGenerator.developGroup.size)

}
import java.util.*
import kotlin.properties.Delegates
import kotlin.random.Random

enum class Grade { RAGULAR, PART_TIME, SALESMAN }
enum class Depart { DEVELOPMENT, CLIENT_SERVICE, OFFICE_SERVICE, SALES }

//RAGULAR (정규직, condition= 0), PART_TIME (파트타임, condition=시간), SALESMAN (영업직, condition=영업실적)
class Employee (var grade: Grade, depart: Department, var condition: Int) {

    companion object {
        const val BASIC_ANNUAL_SALARY = 120_000_000     //연봉
        const val HOURLY_WAGE = 25000                   //시급
        fun getId () = UUID.randomUUID().toString()
        fun getSalary(grade: Grade, condition: Int)= when(grade) {
            Grade.RAGULAR -> BASIC_ANNUAL_SALARY /12
            Grade.PART_TIME -> condition * HOURLY_WAGE
            Grade.SALESMAN -> BASIC_ANNUAL_SALARY /12 + condition * 0.05
        }.toInt()
    }
    val id: String = getId()
    var department by Delegates.observable(
        depart.depart, { props, old, new -> Unit })
    var salary = getSalary(this.grade, this.condition)
}

class Department(grade: Grade) {
    var depart: Depart = getDepartment(grade)
    fun getDepartment(grade: Grade) = when(grade) {
        Grade.SALESMAN -> Depart.SALES
        Grade.RAGULAR -> Depart.values()[Random.nextInt(0, 4)]
        Grade.PART_TIME -> Depart.values()[Random.nextInt(0, 3)]
    }
}

//사원들을 관리하는 employee manager
class EmployeeManager {
    val memberList = arrayListOf<Employee>()
    var total = 0

    fun employeeAdd(e:Employee) = memberList.add(e)

    fun getTotalSalary() {
        total = 0
        for (i in 0 until memberList.size) { total += memberList[i].salary }
        println("천체 사원들의 총월급 : $total 원")
    }

    fun getAverageSalary () {
        getTotalSalary()
        println("천체 사원들의 평균월급 : ${total.div(memberList.size)} 원")
    }

    fun getTotalDepartSalary (depart: Depart) {
        total = 0
        for (i in 0 until memberList.size) { if(memberList[i].department.toString() == depart.name) total += memberList[i].salary }
        println("$depart 부서 총월급 : $total 원")
    }

    fun getAverageDepartSalary (depart: Depart) {
        var count = 0
        for (i in 0 until memberList.size) { if(memberList[i].department.toString() == depart.name) count++ }
        if (count == 0) return
        getTotalDepartSalary(depart)
        println("$depart 부서 평균월급 : ${total.div(count)} 원")
    }
}

//실행
fun main() {

    val employeeManager = EmployeeManager()

    //정규직 직원 추가
    for (i in 0 until 5) {
        var regular = Employee(Grade.RAGULAR, Department(Grade.RAGULAR), 0)
        employeeManager.employeeAdd(regular)
    }
    //파트타임 직원 5명 추가 (140시간 근무)
    for (i in 0 until 5) {
        var partTime = Employee(Grade.PART_TIME, Department(Grade.PART_TIME), 140)
        employeeManager.employeeAdd(partTime)
    }
    //영업직 직원 5명 추가 (영업이익 1억)
    for (i in 0 until 5) {
        var salesMan = Employee(Grade.SALESMAN, Department(Grade.SALESMAN), 100_000_000)
        employeeManager.employeeAdd(salesMan)
    }
    for (i in 0 until 15) {
        println("${employeeManager.memberList[i].grade}, ${employeeManager.memberList[i].department}, ${employeeManager.memberList[i].salary}, ${employeeManager.memberList[i].id}")
    }

    employeeManager.getTotalSalary()
    employeeManager.getAverageSalary()
    employeeManager.getTotalDepartSalary(Depart.OFFICE_SERVICE)
    employeeManager.getAverageDepartSalary(Depart.OFFICE_SERVICE)
    employeeManager.getTotalDepartSalary(Depart.SALES)
    employeeManager.getAverageDepartSalary(Depart.SALES)
    employeeManager.getTotalDepartSalary(Depart.CLIENT_SERVICE)
    employeeManager.getAverageDepartSalary(Depart.CLIENT_SERVICE)
    employeeManager.getTotalDepartSalary(Depart.DEVELOPMENT)
    employeeManager.getAverageDepartSalary(Depart.DEVELOPMENT)
}
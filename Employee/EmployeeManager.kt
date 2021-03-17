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
        fun getId () = UUID.randomUUID()
    }
    val id = getId()
    var department by Delegates.observable(depart.depart, { props, old, new -> Unit })
    var salary = getSalary(this.grade, this.condition)
    fun getSalary(grade: Grade, condition: Int) = when(grade) {
        Grade.RAGULAR -> BASIC_ANNUAL_SALARY /12
        Grade.PART_TIME -> condition * HOURLY_WAGE
        Grade.SALESMAN -> BASIC_ANNUAL_SALARY /12 + condition * 0.05
    }.toInt()
}

class Department(grade: Grade) {
    var depart: Depart = when(grade) {  //랜덤부서배정
        Grade.SALESMAN -> Depart.SALES
        Grade.RAGULAR -> Depart.values()[Random.nextInt(0, 4)]
        Grade.PART_TIME -> Depart.values()[Random.nextInt(0, 3)]
    }
}

//사원들을 관리하는 employee manager
class EmployeeManager {
    val memberList = arrayListOf<Employee>()
    fun employeeAdd(e:Employee) = memberList.add(e) //사원추가
    fun getTotalSalary() : Int {    //모든사원 월급 총합을 계산
        var total = 0
        for (i in 0 until memberList.size) { total += memberList[i].salary }
        return total
    }
    fun getAverageSalary () = getTotalSalary().div(memberList.size) //모든사원 월급 평균을 계산
    fun getTotalDepartSalary (depart: Depart) : Int { //부서(depart) 월급 총합을 계산
        var total = 0
        for (i in 0 until memberList.size) { if(memberList[i].department.toString() == depart.name) total += memberList[i].salary }
        return total
    }
    fun getAverageDepartSalary (depart: Depart) : Int { //부서(depart) 월급 평균을 계산
        var count = 0
        for (i in 0 until memberList.size) { if(memberList[i].department.toString() == depart.name) count++ }
        if (count == 0) return 0
        else return getTotalDepartSalary(depart).div(count)
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
        println("${employeeManager.memberList[i].grade}, ${employeeManager.memberList[i].department}," +
                "${employeeManager.memberList[i].salary}, ${employeeManager.memberList[i].id}")
    }

    println("모든사원 월급 총합 : ${employeeManager.getTotalSalary()}")
    println("모든사원 월급 평균 : ${employeeManager.getAverageSalary()}")
    println("사무팀 월급 총합 : ${employeeManager.getTotalDepartSalary(Depart.OFFICE_SERVICE)}")
    println("사무팀 월급 평균 : ${employeeManager.getAverageDepartSalary(Depart.OFFICE_SERVICE)}")
    println("영업팀 월급 총합 : ${employeeManager.getTotalDepartSalary(Depart.SALES)}")
    println("엽업팀 월급 평균 : ${employeeManager.getAverageDepartSalary(Depart.SALES)}")
    println("고객팀 월급 총합 : ${employeeManager.getTotalDepartSalary(Depart.CLIENT_SERVICE)}")
    println("고객팀 월급 평균 : ${employeeManager.getAverageDepartSalary(Depart.CLIENT_SERVICE)}")
    println("개발팀 월급 총합 : ${employeeManager.getTotalDepartSalary(Depart.DEVELOPMENT)}")
    println("개발팀 월급 평균 : ${employeeManager.getAverageDepartSalary(Depart.DEVELOPMENT)}")
}
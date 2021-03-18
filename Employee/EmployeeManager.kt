import java.util.*
import kotlin.properties.Delegates
import kotlin.random.Random

enum class Grade { RAGULAR, PART_TIME, SALESMAN }
enum class Dep { DEVELOPMENT, CLIENT_SERVICE, OFFICE_SERVICE, SALES }

//RAGULAR (정규직, condition= 0), PART_TIME (파트타임, condition=시간), SALESMAN (영업직, condition=영업실적)
class Employee (var grade: Grade, depart: Department, var condition: Int) {
    companion object {
        const val BASIC_ANNUAL_SALARY = 120_000_000     //연봉
        const val HOURLY_WAGE = 25000                   //시급
        fun getId () = UUID.randomUUID()
    }
    val id = getId()
    var department by Delegates.observable(depart.depart) { props, old, new ->
             println("옮긴 부서 : $new")
    }
    var salary = getSalary(this.grade, this.condition)
    fun getSalary(grade: Grade, condition: Int) = when(grade) {
        Grade.RAGULAR -> BASIC_ANNUAL_SALARY /12
        Grade.PART_TIME -> condition * HOURLY_WAGE
        Grade.SALESMAN -> BASIC_ANNUAL_SALARY /12 + condition * 0.05
    }.toInt()
}

class Department(grade: Grade) {
    var depart: Dep = when(grade) {  //랜덤부서배정
        Grade.SALESMAN -> Dep.SALES
        Grade.RAGULAR -> Dep.values()[Random.nextInt(0, 4)]
        Grade.PART_TIME -> Dep.values()[Random.nextInt(0, 3)]
    }
}

//사원들을 관리하는 employee manager
class EmployeeManager {
    val memberList = arrayListOf<Employee>()
    fun addEmployee(e:Employee) = memberList.add(e) //사원추가
    fun getTotalSalary() : Int {    //모든사원 월급 총합을 계산
        var total = 0
        for (i in 0 until memberList.size) { total += memberList[i].salary }
        return total
    }
    fun getAverageSalary () = getTotalSalary().div(memberList.size) //모든사원 월급 평균을 계산
    fun getTotalDepartSalary (depart: Dep) : Int { //부서(depart) 월급 총합을 계산
        var total = 0
        for (i in 0 until memberList.size) { if(memberList[i].department.toString() == depart.name) total += memberList[i].salary }
        return total
    }
    fun getAverageDepartSalary (depart: Dep) : Int { //부서(depart) 월급 평균을 계산
        var count = 0
        for (i in 0 until memberList.size) { if(memberList[i].department.toString() == depart.name) count++ }
        if (count == 0) return 0
        else return getTotalDepartSalary(depart).div(count)
    }
    fun changeDepart (employee: Employee, newDepart: Dep) {  //부서(depart)이동
        employee.department = newDepart
    }
}

//실행
fun main() {

    val employeeManager = EmployeeManager()

    //정규직 직원 추가
    for (i in 0 until 5) {
        var regular = Employee(Grade.RAGULAR, Department(Grade.RAGULAR), 0)
        employeeManager.addEmployee(regular)
    }
    //파트타임 직원 5명 추가 (140시간 근무)
    for (i in 0 until 5) {
        var partTime = Employee(Grade.PART_TIME, Department(Grade.PART_TIME), 140)
        employeeManager.addEmployee(partTime)
    }
    //영업직 직원 5명 추가 (영업이익 1억)
    for (i in 0 until 5) {
        var salesMan = Employee(Grade.SALESMAN, Department(Grade.SALESMAN), 100_000_000)
        employeeManager.addEmployee(salesMan)
    }
    for (i in 0 until 15) {
        println("${employeeManager.memberList[i].grade}, ${employeeManager.memberList[i].department}," +
                "${employeeManager.memberList[i].salary}, ${employeeManager.memberList[i].id}")
    }

    //정규직 사원 1명(sally) 생성
    var sally = Employee(Grade.RAGULAR, Department(Grade.RAGULAR), 0)
    employeeManager.addEmployee(sally)
    //sally 사원(개발팀으로) 부서이동
    employeeManager.changeDepart(sally, Dep.DEVELOPMENT)

    println("------------------------------------------------")
    println("모든사원 월급 총합 : ${employeeManager.getTotalSalary()}")
    println("모든사원 월급 평균 : ${employeeManager.getAverageSalary()}")
    println("사무팀 월급 총합 : ${employeeManager.getTotalDepartSalary(Dep.OFFICE_SERVICE)}")
    println("사무팀 월급 평균 : ${employeeManager.getAverageDepartSalary(Dep.OFFICE_SERVICE)}")
    println("영업팀 월급 총합 : ${employeeManager.getTotalDepartSalary(Dep.SALES)}")
    println("엽업팀 월급 평균 : ${employeeManager.getAverageDepartSalary(Dep.SALES)}")
    println("고객팀 월급 총합 : ${employeeManager.getTotalDepartSalary(Dep.CLIENT_SERVICE)}")
    println("고객팀 월급 평균 : ${employeeManager.getAverageDepartSalary(Dep.CLIENT_SERVICE)}")
    println("개발팀 월급 총합 : ${employeeManager.getTotalDepartSalary(Dep.DEVELOPMENT)}")
    println("개발팀 월급 평균 : ${employeeManager.getAverageDepartSalary(Dep.DEVELOPMENT)}")
}
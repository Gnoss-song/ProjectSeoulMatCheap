import java.util.*

//부서
enum class Department {
    Develop, // 개발
    Sales, // 영업
    ClientService, // 고객대응
    Biz // 사무직
}
//타입에 따라서
enum class EmployeeType{
    parttime, // 파트타임
    regular, //정규직
    sales //영업직
}

//Employee 시작. uuid, department, wage, employeetype.
open class Employee {
    var uuid: UUID // UUID
    var department: Department
    var wage: Double
    var employeetype : EmployeeType


    constructor(uuid: UUID, department: Department, wage: Double, employeetype: EmployeeType) {
        this.uuid = uuid
        this.department = department
        this.wage = wage
        this.employeetype = employeetype
    }

}
// 정규, 파트타임, 영업직 서브 클래스와 연봉 계산
class Regular(department: Department) : Employee(UUID.randomUUID(),department, wage = 1200000000.0, EmployeeType.regular){
    fun calculateBySalary(salary: Double) = (salary / 12)
}
class PartTime(department: Department) : Employee(UUID.randomUUID(),department,0.0, EmployeeType.parttime){
    fun calculateByHour(hour: Double) = (hour * 160)
}
class SalesPart() : Employee(UUID.randomUUID(),Department.Sales,wage =1200000000000.0, EmployeeType.sales) {
    val performance = readLine()!!.toDouble()
    fun pp(){
        println("영업 실적은 얼마입니까?")
    }
    fun calculateBySales() = (wage + 0.05*performance)

}
// Employee에 대한 정보를 뽑고 싶을때 사용
fun printemp (someone: Employee) {
        println ("==================================")
        println ("사번 : ${someone.uuid}")
        println ("부서 : ${someone.department}")
        println ("타입 : ${someone.employeetype}")
        println ("임금 : ${someone.wage}")
        return
}

fun main() {
    var regul1 = Regular(Department.Biz)
    var regu2 = Regular(Department.Biz)
    var part1 = PartTime(Department.ClientService)
    var sales1 = SalesPart()

//    println(sales1.calculateBySales())
    printemp(regul1)
    printemp(regu2)
    printemp(part1)

}


class EmployeeManager {
    fun partsum(employees: List<Employee>): Double {
        var result = 0.0
        for (i in 0 .. employees.count()-1) {
            result =+ employees[i].wage
        }
        return result
    }

    fun average(employees: List<Employee>): Double {
        return partsum(employees) / employees.count()
    }
}
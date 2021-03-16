import java.util.*
import kotlin.properties.Delegates

open class employee(var eName:String, deptment: department) {
    val eNumber: UUID by lazy { UUID.randomUUID() }
    var eSalary:Int = 0
    var eDepartment: department by Delegates.observable(
        deptment, { _, oldDepartment: department, newDepartment: department ->
            println("${eName}의 부서가 ${oldDepartment.dName}에서 ${newDepartment.dName}으로 바뀌었습니다.")
        })

    constructor(name: String, deptment: department, salary: Int) : this(name, deptment) {
        this.eSalary = salary
    }

    open fun calculateSalary() : Int {
        return eSalary
    }

    override fun toString(): String {
        return "사번 : ${eNumber}, 이름 : ${eName}, 부서 : ${eDepartment.dName}, 월급 : ${calculateSalary()}"
    }
}

class partTimeEmployee(name: String, deptment: department) : employee(name, deptment) {
    var eWorkdayPerMonth = 0

    init{
        eSalary = 25000
    }

    constructor(name:String, deptment: department, workdayPerMonth: Int) : this(name, deptment){
        eWorkdayPerMonth = workdayPerMonth
    }

    override fun calculateSalary(): Int {
        return eSalary * eWorkdayPerMonth
    } // 시급 -> 월급
}

class fullTimeEmployee(name: String, deptment: department) : employee(name, deptment){
    init {
        eSalary = 120_000_000
    }

    override fun calculateSalary(): Int {
        return eSalary / 12
    } // 연봉 -> 월급
}

class salesEmployee(name: String, deptment: department) : employee(name, deptment){
    var eOperatingProfit = 0;
    constructor(name: String, deptment: department, salary: Int, operatingProfit: Int) : this(name, deptment){
        this.eSalary = salary
        this.eName = name
        eOperatingProfit = operatingProfit
    }

    override fun calculateSalary(): Int {
        return eSalary + (eOperatingProfit * 0.05).toInt()
    } // 영업실적 5% 추가
}

abstract class department {
    abstract val dName: String
}

class developDepartment: department() {
    override val dName: String = "개발팀"
}

class salesDepartment: department() {
    override val dName: String = "영업팀"
}
class csDepartment: department() {
    override val dName: String = "고객대응팀"
}
class officeDepartment: department() {
    override val dName: String = "사무팀"
}

class employeeManager(){
    val list : LinkedList<employee> = LinkedList()

    fun employeeAdd(e:employee) = list.add(e)

    fun findEmployeeIndex(i:Int) : employee{
        return list.get(i)
    }

    fun allSalary() : Int{
        var salary = 0;

        for(i in 0 until list.size){
            salary += findEmployeeIndex(i).calculateSalary()
        }

        return salary
    }

    fun averageSalary() : Int{
        return allSalary()/(list.size)
    }
    fun printAllEmployee(){
        for(i in 0 until list.size) println(findEmployeeIndex(i))
    }
    fun deptAllSalary(dName : String):Int{
        var salary = 0;
        for(i in 0 until list.size){
            if(dName == findEmployeeIndex(i).eDepartment.dName){
                salary += findEmployeeIndex(i).calculateSalary()
            }
        }

        return salary
    }

    fun deptAvgSalary(dName: String):Int{
        return deptAllSalary(dName) / list.size
    }

    fun moveDepartment(index : Int,dept : department){
        list.get(index).eDepartment = dept
    }

}

fun main(){
    val d = developDepartment()
    val c = csDepartment()
    val o = officeDepartment()
    val s = salesDepartment() // 부서 설정

    val e1 = employee("한형수", d, 3_000_000)
    val e2 = employee("서태연", d, 7_000_000)
    val e3 = partTimeEmployee("풍도영", c,160)
    val e4 = fullTimeEmployee("배상진", o)
    val e5 = salesEmployee("윤경일", s, 2_000_000, 5_000_000)

    val e6 = employee("문영식", d, 3_000_000)
    val e7 = employee("성서우", d, 7_000_000)
    val e8 = partTimeEmployee("양예빈", c,160)
    val e9 = fullTimeEmployee("홍상현", o)
    val e10 = salesEmployee("배상진", s, 3_000_000, 10_000_000)
    //사원입력

    val em = employeeManager()

    em.employeeAdd(e1)
    em.employeeAdd(e2)
    em.employeeAdd(e3)
    em.employeeAdd(e4)
    em.employeeAdd(e5)
    em.employeeAdd(e6)
    em.employeeAdd(e7)
    em.employeeAdd(e8)
    em.employeeAdd(e9)
    em.employeeAdd(e10)
    //사원 추가

    println(em.printAllEmployee()) // 전체 사원 출력
    println(em.findEmployeeIndex(em.list.size-1))
    println()

    em.moveDepartment(4, c)

    println("전체평균 : ${em.averageSalary()}") // 모든 사원 평균월급 출력
    println("전체평균 : ${em.averageSalary()}") // 모든 사원 월급 합계 출력

    println("개발팀 총 금액 : ${em.deptAllSalary("개발팀")}")
    println("영업팀 총 금액 : ${em.deptAllSalary("영업팀")}")
    println("고객대응팀 총 금액 : ${em.deptAllSalary("고객대응팀")}")
    println("사무팀 총 금액 : ${em.deptAllSalary("사무팀")}") // 각 부서별 사원 총 금액 출력

    println("개발팀 평균 금액 : ${em.deptAvgSalary("개발팀")}")
    println("영업팀 평균 금액 : ${em.deptAvgSalary("영업팀")}")
    println("고객대응팀 평균 금액 : ${em.deptAvgSalary("고객대응팀")}")
    println("사무팀 평균 금액 : ${em.deptAvgSalary("사무팀")}") // 각 부서별 사원 평균 출력


}
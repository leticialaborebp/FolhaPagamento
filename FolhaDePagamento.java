import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Integer;

public class FolhaDePagamento {
    private Scanner scanner = new Scanner(System.in);

    private String showMenu(){
        StringBuilder sb = new StringBuilder();
        sb.append("Digite a opção desejada: \n");
        sb.append("1 - Cadastrar funcionário \n");
        sb.append("2 - Imprimir contracheque \n");
        sb.append("3 - Sair \n");
        System.out.println(sb.toString());
        return scanner.nextLine();
    }

    private Double computeINSSFee(Double salario){
        Double inssFee = 0.0;

        if (salario > 6433.57) {
            inssFee = 751.99;
        } 
        else if (salario <= 1100){
            inssFee = salario*0.075;
        }
        else if (salario > 1100 && salario <= 2203.48){
            inssFee = (1100*0.075) + (salario-1100.01)*0.09;
        }
        else if (salario > 2203.48 && salario <= 3305.22){
            inssFee = (1100*0.075) + (2203.48-1100.01)*0.09 + (salario-2203.48)*0.12;
        }
        else if (salario > 3305.23 && salario <= 6433.57){
            inssFee = (1100*0.075) + (2203.48-1100.01)*0.09 + (3305.24-2203.49) + (salario-3305.23)*0.14;
        }
        return inssFee;
    }

    private Double computeIRRFFee(Double salario,Double inssFee){
        Double irrfFee = 0.0;
        Double salarioSemInss = salario - inssFee;

        if (salarioSemInss > 1903.99 && salarioSemInss <= 2826.65){
            irrfFee = (salarioSemInss*0.075) - 142.8;
        }
        else if (salarioSemInss > 2826.66 && salarioSemInss <= 3751.05){
            irrfFee = (salarioSemInss*0.15) - 354.8;
        }
        else if (salarioSemInss > 3751.06 && salarioSemInss <= 4664.68){
            irrfFee = (salarioSemInss*0.225) - 636.13;
        }
        else if (salarioSemInss > 4664.69){
            irrfFee = (salarioSemInss*0.275) - 869.36;
        }

        return irrfFee;
    }

    public Integer findIndexEmployee(String nameEmployee,  ArrayList<EmployeeClass> employees){
        for(Integer i=0; i<=employees.size()-1; i++){
            if (employees.get(i).getName().equals(nameEmployee)) {
                return i;
            }
        }
        return -1;
    }

    public void start(){
        ArrayList<EmployeeClass> employees = new ArrayList<>();        
        String option = showMenu();

        while (!option.equals("3")){
            switch (option){
                case "1":
                    System.out.println("Digite o nome do funcionário:");
                    EmployeeClass employee = new EmployeeClass();
                    String item = scanner.nextLine();
                    employee.setName(item);
                    System.out.println("Salário bruto:");
                    String bruteSalary = scanner.nextLine();
                    employee.setBruteSalary(Double.parseDouble(bruteSalary));
                    employees.add(employee);
                    option = showMenu();
                    break;
                case "2":
                    System.out.println("Insira o nome do funiconário para imprimir o contracheque: ");
                    String employeeName = scanner.nextLine();
                    Integer indexEmployee = findIndexEmployee(employeeName, employees);
                    if (indexEmployee != -1){
                        System.out.println("Funcionário encontrado ");
                        System.out.println("Gerando contracheque");
                        EmployeeClass selectedEmployee = employees.get(indexEmployee);
                        Double computeInss = computeINSSFee(selectedEmployee.getBruteSalary());
                        selectedEmployee.setInssFee(computeInss);
                        Double computeIrff = computeIRRFFee(selectedEmployee.getBruteSalary(),selectedEmployee.getInssFee());
                        selectedEmployee.setIrrfFee(computeIrff);
                        Double liquidSalary = selectedEmployee.getBruteSalary() -  selectedEmployee.getInssFee() -  selectedEmployee.getIrrfFee();
                        selectedEmployee.setLiquidSalary(liquidSalary);
                        System.out.println("Funcionário: "+ selectedEmployee.getName() + "\n"
                                         + "Salário bruto: "+ selectedEmployee.getBruteSalary() + "\n"
                                         + "INSS: "+ selectedEmployee.getInssFee() + "\n"
                                         + "IRFF: "+ selectedEmployee.getIrrfFee() + "\n"
                                         + "Salário liquido: "+ selectedEmployee.getLiquidSalary() + "\n");
                        option = showMenu();
                        break;
                    }
                    else {
                        System.out.println("Tente novamente");
                        option = showMenu();
                        break;
                    }                    
                default:
                    System.out.println("Opção inválida");
                    option = showMenu();
            }
        }
    
        scanner.close();
        System.out.println("Até logo! ");
    }
   
}
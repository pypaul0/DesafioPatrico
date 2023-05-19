package projeto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		/* 
		 * INSERE TODOS OS FUNCIONARIOS 
		 */
		File file = new File(".\\src\\projeto\\funcionarios.txt");
		FileReader fileReader = new FileReader(file.getAbsolutePath());
		BufferedReader br = new BufferedReader(fileReader);
		
		String linha;
		ArrayList<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
		
		while((linha = br.readLine()) != null) {
			String dados[] = linha.split(";");
			
			Funcionario funcionario = new Funcionario();
			String data_arquivo[] = dados[1].split("-");
				int dia = Integer.parseInt(data_arquivo[0]);
				int mes = Integer.parseInt(data_arquivo[1]);
				int ano = Integer.parseInt(data_arquivo[2]);
			
			LocalDate data = LocalDate.of(ano, mes, dia);
			funcionario.setNome(dados[0]);
			funcionario.setData_nascimento(data);
			funcionario.setSalario(BigDecimal.valueOf(Float.parseFloat(dados[2])));
			funcionario.setFuncao(dados[3]);
			
			listaFuncionarios.add(funcionario);
		}
		br.close();
		
		/*
		 * REMOVE JOÃO DA LISTA
		 */
		for (Funcionario f : listaFuncionarios) {
			if(f.getNome().equals("João")) {
				listaFuncionarios.remove(f);
				break;
			}
		}
		
		/*
		 * EXIBE FUNCIONARIOS
		 */
		for(Funcionario f : listaFuncionarios) {
			System.out.println(f.getNome() + " " +
					f.getData_nascimento().getDayOfMonth() + "/" + f.getData_nascimento().getMonthValue() + "/" + f.getData_nascimento().getYear() + " " + 
					"R$: " + new DecimalFormat("#,###.00").format(f.getSalario()) + " " + 
					f.getFuncao());
		}
		
		/*
		 * 10% DE AUMENTO
		 */
		for(Funcionario f : listaFuncionarios) {
			BigDecimal porcentagem_salario = f.getSalario().multiply(new BigDecimal(0.10));
			f.setSalario(f.getSalario().add(porcentagem_salario));
		}
		
		/*
		 * AGRUPAR POR MAP USANDO FUNÇÃO COMO CHAVE
		 */
		ArrayList<String> funcoes = new ArrayList<>();
		for(Funcionario f : listaFuncionarios) {
			if(!funcoes.contains(f.getFuncao())) {
				funcoes.add(f.getFuncao());
			}
		}
		Map<String, ArrayList<Funcionario>> mapFuncionarios = new HashMap<>();
		for(String func : funcoes) {
			ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
			for(Funcionario f : listaFuncionarios) {
				if(func.equals(f.getFuncao())) {
					funcionarios.add(f);
				}
			}
			mapFuncionarios.put(func, funcionarios);
		}
		mapFuncionarios.forEach((funcao, funcionario) -> {
			System.out.println(funcao);
			funcionario.forEach(f -> System.out.println("- " + f.getNome()));
		});
		
		/*
		 * IMPRIMIR FUNCIONARIOS QUE FAZEM ANIVERSARIO NO MES 10 E 12
		 */
		for(Funcionario f : listaFuncionarios) {
			if(f.getData_nascimento().getMonthValue() == 10 || f.getData_nascimento().getMonthValue() == 12) {
				System.out.println(f.getNome());
			}
		}
		
		/*
		 * FUNCIONARIO MAIS VELHO
		 */
		listaFuncionarios.sort((a, b) -> {
			if(a.getData_nascimento().getYear() == b.getData_nascimento().getYear()) {
				return 0;
			} else if (a.getData_nascimento().getYear() > b.getData_nascimento().getYear()) {
				return 1;
			} else {
				return -1;
			}
		});
		Funcionario funcionario_mais_velho = listaFuncionarios.get(0);
		System.out.println(funcionario_mais_velho.getNome() + ": " +(LocalDate.now().getYear() -  funcionario_mais_velho.getData_nascimento().getYear()));
		
		/*
		 * EXIBIR FUNCIONARIOS EM ORDEM ALFABETICA
		 */
		ArrayList<String> nomes = new ArrayList<String>();
		for(Funcionario f : listaFuncionarios) {
			nomes.add(f.getNome());
		}
		Collections.sort(nomes);
		nomes.forEach(n -> System.out.println(n));
		
		/*
		 * TODOS OS SALARIOS SOMADOS
		 */
		float total = 0;
		for(Funcionario f : listaFuncionarios) {
			total += f.getSalario().floatValue();
		}
		System.out.println("Total: " + total);
		
		/*
		 * QUANTOS SALARIOS MINIMOS GANHA CADA FUNCIONARIO
		 */
		listaFuncionarios.forEach(f -> System.out.println(f.getNome() + " ganha " + ((int) f.getSalario().floatValue() / 1212) + " salarios minimos"));
	}
}

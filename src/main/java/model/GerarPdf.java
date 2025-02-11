package model;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.collections.ObservableList;

public class GerarPdf {

    // Método que gera o PDF
    public void gerarPDF(Stage stage, TableView<DetalhesRetiradasSemDevolucao> tabela) throws Exception {
        // Usando FileChooser para permitir que o usuário escolha onde salvar o PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File arquivo = fileChooser.showSaveDialog(stage);

        if (arquivo != null) {
            // Caminho do arquivo PDF a ser gerado
            String caminhoPDF = arquivo.getAbsolutePath();

            // Criando o documento PDF usando o iText 5
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new java.io.FileOutputStream(caminhoPDF));
            document.open();

            // Adicionando título ao documento
            Paragraph titulo = new Paragraph("Retiradas realizadas");
            titulo.setAlignment(Element.ALIGN_CENTER); // Correção do alinhamento
            document.add(titulo);
            // Adicionando um espaçamento após o título
            document.add(new Paragraph("\n"));  // Um parágrafo vazio (quebra de linha)

            // Construindo a tabela no PDF
            construirTabelaPdf(tabela, document);

            // Fechando o documento após adicionar todos os elementos
            document.close();

            // Após gerar o PDF, abre o arquivo gerado
            abrirPDF(caminhoPDF);

            System.out.println("PDF gerado com sucesso em: " + caminhoPDF);
        }
    }

    // Método para abrir o PDF após ser gerado
    public void abrirPDF(String caminhoPDF) throws IOException {
        File file = new File(caminhoPDF);
        if (file.exists()) {
            // Usando Desktop para abrir o PDF com o programa padrão de PDF
            Desktop.getDesktop().open(file);
        }
    }

    // Método para construir a tabela no PDF a partir de uma TableView do JavaFX
    public void construirTabelaPdf(TableView<DetalhesRetiradasSemDevolucao> tabela, Document document) throws DocumentException {
        // Definindo o número de colunas da tabela
        PdfPTable table = new PdfPTable(5);  // Ajuste o número de colunas conforme necessário

        // Adicionando o cabeçalho da tabela
        table.addCell(createHeaderCell("Item"));
        table.addCell(createHeaderCell("Pessoa"));
        table.addCell(createHeaderCell("Equipamento"));
        table.addCell(createHeaderCell("Quantidade"));
        table.addCell(createHeaderCell("Data da retirada"));

        // Preenchendo a tabela com os dados da TableView
        ObservableList<DetalhesRetiradasSemDevolucao> devo = tabela.getItems();  // Obtém os dados da tabela
        int itemNumber = 1; // Contador para o item
        for (DetalhesRetiradasSemDevolucao d : devo) {
            table.addCell(String.valueOf(itemNumber++));  // Adiciona o número do item
            table.addCell(String.valueOf(d.getNomePessoa()));  
            table.addCell(String.valueOf(d.getNomeEquipamento()));
            table.addCell(String.valueOf(d.getQuantEquipamento()));  // Quantidade
            table.addCell(String.valueOf(d.getDiaRetirada()));  // Quantidade Disponível
    }

        // Adicionando a tabela ao documento PDF
        document.add(table);
}

    // Método para criar células do cabeçalho com fundo colorido
    private PdfPCell createHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);  // Cor de fundo para o cabeçalho
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);  // Centralizando o texto
        return cell;
    }
}

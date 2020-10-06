
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Transformadores {

    private BufferedImage imagem;

    public Transformadores(BufferedImage bufferImage) {
        this.imagem = bufferImage;

    }

    BufferedImage redimensionar(int altura, int largura) {
        BufferedImage imagemRedimensionada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagemRedimensionada.createGraphics();
        g.drawImage(imagem, 0, 0, largura, altura, null);
        g.dispose();

        return imagemRedimensionada;
    }

    BufferedImage espelhamento(int opt) {
        BufferedImage bf = imagem;

        int altura = bf.getHeight();
        int largura = bf.getWidth();

        int[][] matrizImagem = new int[largura][altura];
        int[][] matrizSaida = new int[largura][altura];

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                matrizImagem[x][y] = bf.getRGB(x, y);
            }
        }

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                switch (opt) {
                    case 0:
                        matrizSaida[x][y] = matrizImagem[(largura - 1) - x][y];
                        bf.setRGB(x, y, matrizSaida[x][y]);
                        break;
                    case 1:
                        matrizSaida[x][y] = matrizImagem[x][(altura - 1) - y];
                        bf.setRGB(x, y, matrizSaida[x][y]);
                        break;
                }

            }
        }

        return bf;
    }

    BufferedImage deslocamento(int x, int y
    ) {
        BufferedImage bf = imagem;
        AffineTransform af = new AffineTransform();
        af.translate(x, y);
        BufferedImage afOp = new AffineTransformOp(af, AffineTransformOp.TYPE_BILINEAR).filter(bf, null);
        return afOp;

    }

    BufferedImage rotacionar(int graus, int opcao) {
        BufferedImage bf = imagem;
        if (opcao == 0) {
            graus = graus * (-1);
        }
        graus %= 360;
        if (graus < 0) {
            graus += 360;
        }
        /*AfineTransform: Ela serve para fazer conversões de coordenadas, por exemplo. 
        Serve também para fazer rotações, translações e modificações de escala.*/
        AffineTransform af = new AffineTransform();
        af.rotate(Math.toRadians(graus), (bf.getWidth() / 2.0), (bf.getHeight() / 2.0));
        double x = 0;
        double y = 0;
        if (graus <= 90) {
            x = af.transform(new Point2D.Double(0, bf.getHeight()), null).getX();
            y = af.transform(new Point2D.Double(0.0, 0.0), null).getY();
        } else if (graus <= 180) {
            x = af.transform(new Point2D.Double(bf.getWidth(), bf.getHeight()), null).getX();
            y = af.transform(new Point2D.Double(0, bf.getHeight()), null).getY();
        } else if (graus <= 270) {
            x = af.transform(new Point2D.Double(bf.getWidth(), 0), null).getX();
            y = af.transform(new Point2D.Double(bf.getWidth(), bf.getHeight()), null).getY();
        } else {
            x = af.transform(new Point2D.Double(0, 0), null).getX();
            y = af.transform(new Point2D.Double(bf.getWidth(), 0), null).getY();
        }

        AffineTransform t = new AffineTransform();
        t.translate(-x, -y);
        af.preConcatenate(t);

        BufferedImage bfIm = new AffineTransformOp(af, AffineTransformOp.TYPE_BILINEAR).filter(bf, null);

        return bfIm;

    }

    BufferedImage filtroSobel() {
        BufferedImage bf = filtroGauss();

        int x = bf.getWidth();
        int y = bf.getHeight();

        int maxGval = 0;
        int[][] coresBordas = new int[x][y];
        int maxGradient = -1;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {

                int val00 = escalaCinza(bf.getRGB(i - 1, j - 1));
                int val01 = escalaCinza(bf.getRGB(i - 1, j));
                int val02 = escalaCinza(bf.getRGB(i - 1, j + 1));

                int val10 = escalaCinza(bf.getRGB(i, j - 1));
                int val11 = escalaCinza(bf.getRGB(i, j));
                int val12 = escalaCinza(bf.getRGB(i, j + 1));

                int val20 = escalaCinza(bf.getRGB(i + 1, j - 1));
                int val21 = escalaCinza(bf.getRGB(i + 1, j));
                int val22 = escalaCinza(bf.getRGB(i + 1, j + 1));

                int gx = ((-1 * val00) + (0 * val01) + (1 * val02))
                        + ((-2 * val10) + (0 * val11) + (2 * val12))
                        + ((-1 * val20) + (0 * val21) + (1 * val22));

                int gy = ((-1 * val00) + (-2 * val01) + (-1 * val02))
                        + ((0 * val10) + (0 * val11) + (0 * val12))
                        + ((1 * val20) + (2 * val21) + (1 * val22));

                double gval = Math.sqrt((gx * gx) + (gy * gy));
                int g = (int) gval;

                if (maxGradient < g) {
                    maxGradient = g;
                }

                coresBordas[i][j] = g;
            }
        }

        double escala = 255.0 / maxGradient;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = coresBordas[i][j];
                edgeColor = (int) (edgeColor * escala);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                bf.setRGB(i, j, edgeColor);
            }
        }
        return bf;

    }

    public static int escalaCinza(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

        return gray;
    }

    BufferedImage negativo() {
        BufferedImage bf = imagem;

        for (int y = 0; y < bf.getHeight(); ++y) {
            for (int x = 0; x < bf.getWidth(); ++x) {
                Color pixel = new Color(bf.getRGB(x, y));

                Color negativo = new Color(
                        255 - pixel.getRed(),
                        255 - pixel.getGreen(),
                        255 - pixel.getBlue());

                bf.setRGB(x, y, negativo.getRGB());
            }
        }
        return bf;
    }

    BufferedImage brilho(int brilho) {

        BufferedImage bf = imagem;

        for (int i = 0; i < bf.getWidth(); i++) {
            for (int j = 0; j < bf.getHeight(); j++) {

                Color c = new Color(bf.getRGB(i, j));

                int vermelho = c.getRed();
                int verde = c.getGreen();
                int azul = c.getBlue();

                vermelho = (1 * vermelho + brilho);
                verde = (1 * verde + brilho);
                azul = (1 * azul + brilho);

                vermelho = Math.max(Math.min(255, vermelho), 0);
                verde = Math.max(Math.min(255, verde), 0);
                azul = Math.max(Math.min(255, azul), 0);

                Color pixel_final = new Color((int) vermelho, (int) verde, (int) azul);
                bf.setRGB(i, j, pixel_final.getRGB());

            }
        }
        return bf;
    }

    BufferedImage filtroGauss() {
        BufferedImage bf = imagem;
        int[][] gaussiana = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        int[][] matriz = new int[bf.getWidth()][bf.getHeight()];

        int soma, vlr = 0;

        for (int w = 1; w < bf.getWidth() - 1; w++) {
            for (int h = 1; h < bf.getHeight() - 1; h++) {
                Color c = new Color(bf.getRGB(w, h));
                int cinza = ((c.getRed() + c.getGreen() + c.getBlue()) / 3);
                matriz[w][h] = cinza;
            }
        }

        for (int w = 1; w < bf.getWidth() - 1; w++) {
            for (int h = 1; h < bf.getHeight() - 1; h++) {
                soma = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        soma += matriz[w + (i - 1)][h + (j - 1)] * gaussiana[i][j];
                    }
                    vlr = soma / 16;
                }
                Color c = new Color((int) vlr, (int) vlr, (int) vlr);
                bf.setRGB(w, h, c.getRGB());
            }
        }

        return bf;
    }

    BufferedImage filtroMediana() {

        BufferedImage bf = imagem;

        int largura = bf.getWidth();
        int altura = bf.getHeight();

        int[][] mascara = new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        int[][] matrizImagem = new int[largura][altura];
        int[][] cinzaRGB = new int[largura][altura];

        int z = 0, cor = 0, xx = 0, yy = 0;

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                int r = ((bf.getRGB(x, y) & 0xFF0000) >> 16);
                int g = ((bf.getRGB(x, y) & 0xFF00) >> 8);
                int b = (bf.getRGB(x, y) & 0xFF);
                matrizImagem[x][y] = (r + g + b) / 3;
            }
        }

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                z = 0;
                xx = 0;
                yy = 0;
                cor = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        yy = y + j;
                        if (xx < largura && yy < altura) {
                            cor = matrizImagem[xx][yy];
                            z += cor * mascara[i][j];
                        }
                    }
                    xx = x + i;
                }
                matrizImagem[x][y] = z / 9;
                cinzaRGB[x][y] = (0xFF000000) | ((matrizImagem[x][y] << 16) & 0x00FF0000) | ((matrizImagem[x][y] << 8) & 0x0000FF00) | (matrizImagem[x][y] & 0x000000FF);  // converte a informação de rgb para inteiro 
                bf.setRGB(x, y, cinzaRGB[x][y]);
            }
        }
        return bf;
    }

    BufferedImage contraste(int contraste) {
        BufferedImage bf = imagem;
        int pixel = 0;

        for (int i = 0; i < bf.getWidth(); i++) {
            for (int j = 0; j < bf.getHeight(); j++) {
                Color c = new Color(bf.getRGB(i, j));
                int cor = ((c.getRed() + c.getGreen() + c.getBlue()) / 3);

                pixel = contraste * cor + 0;

                if (pixel > 255) {
                    pixel = 255;
                } else if (pixel < 0) {
                    pixel = 0;
                }

                Color px = new Color((int) pixel, (int) pixel, (int) pixel);
                bf.setRGB(i, j, px.getRGB());

            }
        }

        return bf;
    }

}

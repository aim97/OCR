function y = ocrProject(x,resultPath,filename)
image   = imread(x);
ocrResults     = ocr(image);
resultPath = strcat(resultPath , filename);
resultPath = strcat(resultPath ,'.txt');
fid = fopen(resultPath,'w');
fwrite(fid,ocrResults.Text);
fclose(fid);
%msgbox('done extracting text from image to text file','done! aim97 rules');
% to add image to the dialog do this
icon = imread('images1.jpg');
waitfor(msgbox('done extracting text from image to text file','done! aim97 rules','custom',icon));
exit
%text(600, 150, recognizedText, 'BackgroundColor', [1 1 1]);